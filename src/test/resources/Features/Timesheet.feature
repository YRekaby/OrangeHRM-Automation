@TimesheetSubmission
Feature: Timesheet Submission

  Scenario: Employee submits a weekly timesheet

    Given Employee logs in system
    When Employee opens My Timesheet
    And Employee fills and submits the timesheet
    Then Timesheet should be submitted successfully
    Given Admin logs into the Time module
    When Admin opens the employee submitted timesheet
    And Admin approves the timesheet
    Then Timesheet should be approved successfully