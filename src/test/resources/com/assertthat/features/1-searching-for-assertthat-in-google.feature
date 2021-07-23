# language: en
@Product1    @criticals    
Feature: Searching for AssertThat in Google

    I want to find AssertThat in Google search results

    @AUTOMATED @DEMO-1 @DEMO-14 
    Scenario: Searching for AssertThat in Google
        
        Given I am on Google home page
        When I search for "AssertThat bdd"
        Then I see that the first result is "AssertThat-BDD & Cucumber for Jira | Atlassian Marketplace new"

