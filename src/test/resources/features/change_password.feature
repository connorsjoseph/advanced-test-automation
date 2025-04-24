Feature: Change Password

  As a registered user
  I want to change my password
  So that I can secure my account

  Background:
    Given user is on the login page
    When user enters valid username "john" and password "demo"
    And clicks the login button
    Then user should be navigated to the dashboard

  Scenario: User changes password successfully
    When user enters the old password "oldpassword123" and the new password "newpassword123"
    And clicks the change password button
    Then the password should be updated successfully

  Scenario: User tries to change password with incorrect old password
    When user enters the old password "incorrectpassword" and the new password "newpassword123"
    And clicks the change password button
    Then the user should see an error message indicating incorrect old password
