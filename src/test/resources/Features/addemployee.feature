@AddEmployee
Feature: Adding new employee

  Scenario: Admin creates a new employee
    Given Admin is logged in
    When Admin navigates to Add Employee Page
    And Admin enters employee details
    And Admin saves the employee
    And Admin assigns the employee role
    And Admin logs out
    And Employee logs in with assigned credentials
    Then Employee should have the correct access rights