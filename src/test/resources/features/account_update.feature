Feature: Account Update

  As a registered user
  I want to update my personal information
  So that I can keep my details current

  Background:
    Given user is on the login page
    When user enters valid username "john" and password "demo"
    And clicks the login button
    Then user should be navigated to the dashboard

  Scenario: User updates email address
    When user updates the email address to "newemail@example.com"
    And clicks the save button
    Then the email address should be updated successfully

  Scenario: User updates phone number
    When user updates the phone number to "+1234567890"
    And clicks the save button
    Then the phone number should be updated successfully
