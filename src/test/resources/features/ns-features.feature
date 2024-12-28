Feature: User Management

  Scenario: Create a new user
    Given the userName user1 does not exist
    When a create user with userName user1 is submitted
    Then the user should be created successfully