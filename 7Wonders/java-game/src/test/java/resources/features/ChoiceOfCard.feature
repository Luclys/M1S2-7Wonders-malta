Feature: Player chooses a card

  Background:
    Given a player with id 123456
    And a player has a card TAVERNE

  Scenario: play TAVERNE
    When player chooses TAVERNE
    Then 5 coins added to player
    And this card is unavailable