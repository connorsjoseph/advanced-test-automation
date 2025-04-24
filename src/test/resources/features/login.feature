@Login
Feature: Login Functionality

  As a registered user
  I want to log into the banking application
  So that I can access my dashboard

  Scenario: Successful login with valid credentials
    Given user is on the login page
    When user enters valid username "john" and password "demo"
    And clicks the login button
    Then user should be navigated to the dashboard
