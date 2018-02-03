@removeAutoMobile
Feature: Remove automobile info which i bought

   Scenario: user enters the correct details 
    Given Web context is setup 
    When client requests POST '/api/automobile/delete' with JSON data 
	"""
	{"id":"1","email":"chakradhar@gmail.com","VehicleNo":"TN 02 B 1234"}
	"""
	Then response code should be 200 
	  And user Vehicle no should be deleted from DB for Id 1
	  And Response body should be 'Automobile info deleted Successfully'
	  
   Scenario: user submits with a blank Vehicle Number
    Given Web context is setup 
	When client requests POST '/api/automobile/delete' with JSON data  
	"""
	{"id":"1","email":"chakradhar@gmail.com","VehicleNo":""}
	"""
	Then response code should be 400
	  And Response body should be 'Vehicle Number should not be blank'	  
	
  Scenario: user enters an Vehicle Number which is not registered under user 
    Given Web context is setup
     And following Vehicle Number mapped with different user
	  | id | name                  | email                         | VehicleNo     |
	  | 1  | chakradhar            | chakradhar@gmail.com          | TN 02 B 1234  |
	  | 2  | vignesh			   | vignesh.r90@gmail.com		   | 			   | 
	When client requests POST '/api/automobile/delete' with JSON data  
	"""
	{"id":"1","email":"chakradhar@gmail.com","VehicleNo":"TN 02 B 1237"}
	"""
	Then response code should be 400
	  And Response body should be 'Vehicle Number you entered is not registered under your name'

