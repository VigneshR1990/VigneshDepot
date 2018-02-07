@registration
Feature: register directly here

  Scenario: user enters the correct details
    Given Web context is setup
    And User DB is empty
    When client requests POST /api/user/add with JSON data
      """
      {"name":"vignesh","email":"vignesh.r90@gmail.com","password":"pwd12345","enabled":true}
      """
    Then response code should be 200
    And user should be stored in DB with id 1
    And Response body should be 'User registration successful'
    And Response header should have 'location' with value 'http://localhost:8888/api/user/1'

  Scenario: user request details
    Given Web context is setup
    And User is logged in with UserName : 'vignesh.r90@gmail.com' and Password : 'pwd12345'
    When client requests GET /api/user/1
    Then response code should be 200
    And Response JSON should be
      """
      {
      "id": 1,
      "name": "vignesh",
      "email": "vignesh.r90@gmail.com",
      "enabled": true
      }
      """
