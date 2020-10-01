Feature: Player discards card

  Scenario: discard a card
    Given Player has a card
    When player discards a card
    Then 3 coins added to player
    And the card is no more available