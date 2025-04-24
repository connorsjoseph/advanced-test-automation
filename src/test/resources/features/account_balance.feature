Feature: Account Balance

  As a registered user
  I want to view my account balance
  So that I can keep track of my funds

  Background:
    Given user is on the login page
    When user enters valid username "john" and password "demo"
    And clicks the login button
    Then user should be navigated to the dashboard

  Scenario: User checks account balance
    When user navigates to the account balance section
    Then user should see the correct account balance
