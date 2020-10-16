Feature: Two players have a conflict
  At the end of each Age, each player has a conflict with each one of his neighbors

  Scenario: Resolving conflict between players
    Given player has three shields
    And right neighbor has two shields
    And left neighbor has four shields
    When the players engage in the conflict
    Then player should win one conflict point
    And the player s defeatChips is incremented