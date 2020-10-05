package gameelements;

public abstract class Effect {
    // 0 = instantaneous, 1 = end of the game, 2 = whenever player wants
    int delay = 0;
    // 0 = once, 1 every turn, 2 every Age
    int repeat = 0;


    public Effect(int delay, int repeat) {
        this.delay = delay;
        this.repeat = repeat;
    }

    public Effect() {}

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect(Inventory inv) {
/*
        if (delay == 0 && repeat == 0) {
        }
*/
    }
}
