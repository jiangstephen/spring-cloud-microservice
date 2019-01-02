Scenario: when a user increases a counter, its value is increased by 3
 
Given a counter
And the counter has the initial value 10
When the user increases the counter
Then the value of the counter must be 11

Scenario: when a user increases a counter, its value is increased by 4
 
Given a counter
And the counter has the initial value 13
When the user increases the counter
When the user increases the counter
Then the value of the counter must be 15