Feature: Fund Transfer

  As a registered user
  I want to transfer funds to another account
  So that I can manage my finances

  Background:
    Given user is on the login page
    When user enters valid username "john" and password "demo"
    And clicks the login button
    Then user should be navigated to the dashboard

  Scenario: User transfers funds successfully
    When user enters the recipient account number "987654321" and the transfer amount "$100"
    And clicks the transfer button
    Then the transfer should be successful and the balance should be updated

  Scenario: User tries to transfer an amount greater than the balance
    When user enters the recipient account number "987654321" and the transfer amount "$10000"
    And clicks the transfer button
    Then the user should see an error message indicating insufficient funds
