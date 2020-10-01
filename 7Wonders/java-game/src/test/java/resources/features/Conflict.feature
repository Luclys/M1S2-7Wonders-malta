Feature: Two players have a conflict
  At the end of each Age, each player has a conflict with each one of his neighbors

  Background:
    Given a player 0 has 3 shields
    And a player 1 has 2 shields

  Scenario: Resolving conflict between players
    When the two players engage in the conflict
    Then player 0 should win 1 conflict point