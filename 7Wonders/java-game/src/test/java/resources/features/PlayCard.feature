Feature: A player plays a card and wants to perform an action with it

  Background:
    Given a player has chosen a card

  Scenario:
    Given a player can build his Wonder's step
    When a player wants to build his Wonder's step
    Then we have to check if the player can build his Wonder's step
    And a player builds his Wonder's step

  Scenario:
    Given a player cannot build his Wonder's step
    When a player wants to build his Wonder's step
    Then we have to check if the player can build his Wonder's step
    And has to choose another action to perform

