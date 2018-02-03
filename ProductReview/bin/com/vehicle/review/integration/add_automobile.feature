@addAutoMobile
Feature: add automobile info which i bought

 Scenario: user enters the correct details 
    Given Web context is setup 
    When client requests POST '/api/automobile/add' with JSON data 
	"""
	{"id":"1","email":"chakradhar@gmail.com","VehicleNo":"TN 02 B 1234"}
	"""
	Then response code should be 200 
	  And user Vehicle no should be updated in DB with 'TN 02 B 1234' for Id 1
	  And Response body should be 'Automobile info Added Successfully'
	  
   Scenario: user submits with a blank Vehicle Number
    Given Web context is setup 
	When client requests POST '/api/automobile/add' with JSON data  
	"""
	{"id":"1","email":"chakradhar@gmail.com","VehicleNo":""}
	"""
	Then response code should be 400
	  And Response body should be 'Vehicle Number should not be blank'	  
	
  Scenario: user enters an Vehicle Number which is already there in DB 
    Given Web context is setup 
     And following Vehicle Number exists
	  | id | name                  | email                         | VehicleNo     |
	  | 1  | chakradhar            | chakradhar@gmail.com          | TN 02 B 1234  |
	  | 2  | vignesh			   | vignesh.r90@gmail.com		   | 			   |
	When client requests POST '/api/automobile/add' with JSON data  
	"""
	{"id":"2","email":"vignesh@gmail.com","VehicleNo":"TN 02 B 1234"}
	"""
	Then response code should be 400
	And Response body should be 'Vehicle Number you entered already exists in DB'

