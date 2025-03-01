@register
Feature: Forceget User Registration and Login

  @smoke
  Scenario: Register with Fixed Values and Login
    Given user is on the registration page
    When user enters the following details:
      | email     | k95599k@gmail.com |
      | password  | Ultra1234!        |
    And clicks on register button
    Then registration should be successful
    When user navigates to login page
    And user logs in with registered credentials
    Then login should be successful