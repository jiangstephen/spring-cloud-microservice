To get jwt token from the authroization server

curl clientId:secret@localhost:8090/oauth/token -d grant_type=password -d username=admin -d password=admin

and the token look like below

{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MzI4Nzk3MTksImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwianRpIjoiMDA5OWU3YTItYmIxMS00M2QzLWI5NTAtZjU1MTc0NGIzZjI4IiwiY2xpZW50X2lkIjoiY2xpZW50SWQifQ.cBs2qfS7DODrtNaBa02A2-bMi_9RaQy4Kj8_ClusX3U","token_type":"bearer","expires_in":43199,"scope":"read write","jti":"0099e7a2-bb11-43d3-b950-f551744b3f28"}


To run the integration test, start the Authorization server first