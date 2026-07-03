@AddEmergencyAndDependent
Feature: Emergency Contacts and Dependents Management

  Scenario: Employee manages emergency contacts and dependents

    Given Employee logs in
    When Employee deletes the existing emergency contact
    And Employee adds a new emergency contact
    And Employee adds a dependent
    Then Emergency contact and dependent should be saved successfully
    Given Admin logs into the system
    When Admin navigates to employee profile
    Then Admin reviews and confirm updates