package strategy;

import board.Board;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.effects.ChoiceResourceEffect;
import gameelements.effects.ResourceEffect;
import gameelements.enums.Action;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class RuleBasedAI implements PlayingStrategy {
    ArrayList<Inventory> censoredInvList = null;
    Action action;
    Card chosenCard;
    private int age = 0;
    private int rightNeighborId;
    private int leftNeighborId;

    Board board;

    @Override
    public Card chooseCard(Inventory inventory, Board b) throws Exception {
        ArrayList<Card> cardsAvailable = cardsAvailableToPlay(inventory);
        this.board = b;
        /*this.action = Action.SELL;
        chosenCard =inventory.getCardsInHand().get(0)*/;

        // REGLE 6 (Partie 1) - a random remaining card is played if possible
        if (cardsAvailable.isEmpty()) {
           this.action = Action.SELL;
            chosenCard =inventory.getCardsInHand().get(0);
            return chosenCard;
        }

        ArrayList<Card> cardsBuildable = cardsAvailable;
        cardsBuildable.removeIf(card -> !(inventory.canBuildCardForFree(card) || inventory.canBuild(card.getRequiredResources())));


        // REGLE 1 - a card providing 2 or more resource types
        // On retire toutes les cartes qui ne sont pas constructibles gratuitement ou avec les ressources disponibles

        ArrayList<Card> moreThanOneResourceType = new ArrayList<>();
        moreThanOneResourceType.add(CardsSet.FORUM);
        moreThanOneResourceType.add(CardsSet.CARAVANSERAIL);

        moreThanOneResourceType.add(CardsSet.EXCAVATION);
        moreThanOneResourceType.add(CardsSet.FOSSE_ARGILEUSE);
        moreThanOneResourceType.add(CardsSet.EXPLOITATION_FORESTIERE);
        moreThanOneResourceType.add(CardsSet.GISEMENT);
        moreThanOneResourceType.add(CardsSet.MINE);


        for (Card card : cardsBuildable) {
            if (moreThanOneResourceType.contains(card)) {
                this.action = Action.BUILDING;
                chosenCard = card;
                return chosenCard;
            }
        }

        // REGLE NUMERO 2 - a card providing a single resource type that is lacking
        // On compile toutes les ressources détenues possibles et on cherche s'il y en a une à 0
        int[] resources = inventory.getAvailableResources().clone();
        for (Resource[] pair : inventory.getPairResChoice()) {
            for (Resource res : pair) {
                resources[res.getIndex()] += 1;
            }
        }
        if (inventory.getAnyMatierePremiereAvailableCount() != 0) {
            resources[Resource.ARGILE.getIndex()] += 1;
            resources[Resource.PIERRE.getIndex()] += 1;
            resources[Resource.MINERAI.getIndex()] += 1;
            resources[Resource.BOIS.getIndex()] += 1;
        }
        if (inventory.getProduitsManifacturesPrice() != 0) {
            resources[Resource.VERRE.getIndex()] += 1;
            resources[Resource.TISSU.getIndex()] += 1;
            resources[Resource.PAPYRUS.getIndex()] += 1;
        }

        for (int i = 0; i < resources.length; i++) {
            if (resources[i] == 0) {
                // On regarde dans les cartes Buildables s'il y a un effet qui nous donne la ressource manquante
                for (Card card : cardsBuildable) {
                    if (card.getEffects()[0] instanceof ResourceEffect) {
                        ResourceEffect effect = (ResourceEffect) card.getEffects()[0];
                        if (effect.getResource().getIndex() == i) {
                            this.action = Action.BUILDING;
                            chosenCard = card;
                            return chosenCard;
                        }
                    }
                    if (card.getEffects()[0] instanceof ChoiceResourceEffect) {
                        ChoiceResourceEffect effect = (ChoiceResourceEffect) card.getEffects()[0];
                        for (Resource res : effect.getResources()) {
                            if (res.getIndex() == i) {
                                this.action = Action.BUILDING;
                                chosenCard = card;
                                return chosenCard;
                            }
                        }
                    }
                }
            }
        }

        // REGLE NUMERO 3 - if IA is not the only leader in military, and the card allows rbAI to become the (or one of the) leading military player(s)
        ArrayList<Card> militaryCards = new ArrayList<>(cardsBuildable);
        militaryCards.removeIf(card -> card.getCategory() != Category.BATIMENT_MILITAIRE);

        if (militaryCards.size() != 0) {
            int playerShields = inventory.getSymbolCount(Symbol.BOUCLIER);

            if (!isOnlyLeader(playerShields)) {
                militaryCards.sort(Comparator.comparingInt(card -> card.getEffects()[0].getConstantlyAddedItem()));
                Card optimalCard = militaryCards.get(militaryCards.size() - 1);

                if (isAmongLeaders(playerShields)) {
                    action = Action.BUILDING;
                    chosenCard =optimalCard;
                    return chosenCard;
                } else {
                    int nbBoubou = optimalCard.getEffects()[0].getConstantlyAddedItem();
                    nbBoubou += playerShields;

                    if (isAmongLeaders(nbBoubou)) {
                        action = Action.BUILDING;
                        chosenCard =optimalCard;
                        return chosenCard;
                    }
                }
            }
        }

        // REGLE 4 - play civil card
        ArrayList<Card> civilCards = new ArrayList<>(cardsBuildable);
        civilCards.removeIf(card -> card.getCategory() != Category.BATIMENT_CIVIL);
        if (civilCards.size() != 0) {
            civilCards.sort(Comparator.comparingInt(card -> card.getEffects()[0].getConstantlyAddedItem()));
            action = Action.BUILDING;
            chosenCard = civilCards.get(civilCards.size() - 1);
            return chosenCard;
        }

        // REGLE 5 - play science card
        ArrayList<Card> scienceCards = new ArrayList<>(cardsBuildable);
        scienceCards.removeIf(card -> card.getCategory() != Category.BATIMENT_SCIENTIFIQUE);
        if (scienceCards.size() != 0) {
            action = Action.BUILDING;
            chosenCard = scienceCards.get(0);
            return chosenCard;
        }

        // REGLE 6 (Partie 1) - a random remaining card is played if possible
        if (cardsBuildable.isEmpty()) {
            this.action = Action.SELL;
            this.chosenCard = inventory.getCardsInHand().get(0);
            return chosenCard;
        } else {
            Random r = new Random();
            int rand = r.nextInt(cardsBuildable.size());
            action = Action.BUILDING;
            chosenCard = cardsBuildable.get(rand);
            return chosenCard;
        }
    }

    private boolean isOnlyLeader(int nbBoubou) {
        int playerBoubouRight = censoredInvList.get(rightNeighborId).getSymbolCount(Symbol.BOUCLIER);
        int playerBoubouLeft = censoredInvList.get(leftNeighborId).getSymbolCount(Symbol.BOUCLIER);

        return nbBoubou > playerBoubouLeft && nbBoubou > playerBoubouRight;
    }

    private boolean isAmongLeaders(int nbBoubou) {
        int playerBoubouRight = censoredInvList.get(rightNeighborId).getSymbolCount(Symbol.BOUCLIER);
        int playerBoubouLeft = censoredInvList.get(leftNeighborId).getSymbolCount(Symbol.BOUCLIER);

        return (nbBoubou == playerBoubouLeft && nbBoubou > playerBoubouRight)
                || (nbBoubou == playerBoubouRight && nbBoubou > playerBoubouLeft)
                || (nbBoubou == playerBoubouLeft && nbBoubou == playerBoubouRight);
    }



    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public Card getCard() {
        return chosenCard;
    }

    @Override
    public PlayingStrategy copy() {
        RuleBasedAI s = new RuleBasedAI();
        s.chosenCard = this.chosenCard;
        s.action = this.action;
        return s;
    }

    @Override
    public void updateKnowledge(ArrayList<Inventory> censoredInvList, int age, int currentTurn, int rightNeighborId, int leftNeighborId) {
        this.censoredInvList = censoredInvList;
        this.age = age;
        this.rightNeighborId = rightNeighborId;
        this.leftNeighborId = leftNeighborId;
    }
}
