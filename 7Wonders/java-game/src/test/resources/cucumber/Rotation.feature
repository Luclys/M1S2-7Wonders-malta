Feature: card rotation

  Scenario: left rotation
    Given Inventories have cards
    When left rotation
    Then inventories change cards between them in left direction

    Given Inventories have cards
    When right rotation
    Then inventories change cards between them in right direction

