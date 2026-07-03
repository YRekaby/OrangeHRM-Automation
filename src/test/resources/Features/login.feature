@LoginSuccessfully
Feature: Login

  Scenario: Successful Login
    Given user is on login page
    When user logs in with valid credentials
    Then dashboard should be displayed
