@editMobileNo
Feature: edit mobile number

   Scenario: user enters the correct details 
    Given Web context is setup 
    When client requests POST '/api/mobno/edit' with JSON data 
	"""
	{id":"2","email":"vignesh@gmail.com","oldNo":"9787240827","newNo":"9787240828"}
	"""
	Then response code should be 200 
	  And user mobile no should be updated in DB with 9787240828 for id 2
	  And Response body should be 'Mobile Number Updated Successfully'
	
  Scenario: user enters the incorrect mobile number 
    Given Web context is setup
     And Mobile no is not numeric for id 2 
	When client requests POST '/api/mobno/edit' with JSON data  
	"""
	{id":"2","email":"vignesh@gmail.com","oldNo":"9787240827","newNo":"av787240828"}
	"""
	Then response code should be 400
	  And Response body should be 'Incorrect Mobile Number : Mobile numbers should be numeric'

  Scenario: user enters the incorrect mobile number  
    Given Web context is setup 
     And Mobile no is not equal to 10 digits for id 2
	When client requests POST '/api/mobno/edit' with JSON data
	"""
	{id":"2","email":"vignesh@gmail.com","oldNo":"9787240827","newNo":"9799787240828"}
	"""
	Then response code should be 400
	   And Response body should be 'Incorrect Mobile Number : Mobile numbers should not be greater that ten numbers'

 

