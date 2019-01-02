Narrative:
Test spring reactor using jbehave
using multiple threads to concurrently run the scenarios in the story
The goal is to 
Define the bean scope for each scenario
After each scenario, the transaction should be rolled back

Scenario: Save the movie and restore the movie

Given the movie 1 with name movie1 is stored
Then the movie with id 1 should have name movie1


Scenario: only restore the movie

Given doing nothing
Then the movie with id 1 should have name movie1
