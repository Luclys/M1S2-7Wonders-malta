Feature: Player discards card

  Scenario: discard a card
    Given Inventory has a card
    When inventory discards a card
    Then coins added to inventory
    And the card is no more available

