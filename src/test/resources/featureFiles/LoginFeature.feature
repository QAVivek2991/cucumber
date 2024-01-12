#Author: vivek.kumar@mindruby.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template


@tag
Feature: Login page features for connect panel

  @runthis
 	Scenario: Verify logo, connect text and placeholder
    Then Verify that logo is visible
    And Verify that connect text is visible
    And Verify placeholder of email and password field
    
	 @runthis
	Scenario: Verify Remember me, Sine in button and Forgot Password is clickable
		Then Verify that Remember me is clickable
		And Verify that Forgate Password is clickable
		And Verify that Sine in button is clickable
		
	Scenario: Verify that user is not able to login with invalid credentials and error message id displayed
		When User enter "vivek.admin.connect@yopmail.com" as email
		And User enter "12335" as password
		And User click on sine in button
		Then Error message should display as "Invalid Username or Password"
		But Verify that user is not logged in
		

	Scenario Outline: Verify that user is able to login with valid credentials
		When User enter "<email>" as email
		And User enter "<password>" as password
		And User click on sine in button
		Then User logged in and username is visible
		
		Examples:
		|email														|password|
		|vivek.admin.connect@yopmail.com	|1233		 |
		|vivek.admin.connect1@yopmail.com	|1233		 |
