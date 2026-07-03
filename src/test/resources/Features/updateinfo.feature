@UpdatePersonalInfo
Feature: Update Personal Info

  Scenario: Update Info
    Given Employee is logged in
    When Employee updates personal information
    And Employee logs out
    And Admin logs in
    And Admin opens employee profile
    Then Admin should see the updated personal information