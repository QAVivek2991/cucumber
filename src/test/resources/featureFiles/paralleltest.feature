#Author: your.email@your.domain.com
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
Feature: Parallel test 
  I want to use this template for my feature file
  
 @runthis
  Scenario: Verify logo, connect text and placeholder
    Then Verify that logo is visible
    And Verify that connect text is visible
    And Verify placeholder of email and password field
    
	
	Scenario: Verify Remember me, Sine in button and Forgot Password is clickable
		Then Verify that Remember me is clickable
		And Verify that Forgate Password is clickable
		#And Verify that Sine in button is clickable