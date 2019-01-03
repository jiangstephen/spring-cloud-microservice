Narrative:
Test spring reactor using jbehave
using multiple threads to concurrently run the scenarios in the story
The goal is to 
Define the bean scope for each scenario
After each scenario, the transaction should be rolled back

Scenario: Save the movie and restore the movie

Given in h2, the movie 1 with name movie1 is stored
Given in h2, the movie 2 with name movie2 is stored 
Then in h2, the movie with id 1 should have name movie1
Then in h2, the movie with id 2 should have name movie2


Scenario: only restore the movie

Given in h2, doing nothing
Then in h2, the movie with id 1 should have name movie1
Then in h2, the movie with id 3 should not exist
Then in h2, the movie with id 2 should have name movie2