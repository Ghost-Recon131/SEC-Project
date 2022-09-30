# Endpoint Documentation

Current Domain is localhost: http://localhost:8080/
# Accounts
## Register account
Endpoint URI: /api/RegisterLogin/register
```json
{
    "username": "test-register",
    "email": "test@gmail.com",
    "firstname": "initial",
    "lastname": "test",
    "password": "test123",
    "secret_question": "secret_question",
    "secret_question_answer": "secret_question_answer"
}
```
- Returns a string message for outcome of registration 


## Login
Endpoint URI: /api/RegisterLogin/login
```json
{
  "username": "test-register",
  "password": "test123"
}
```
- Returns result of action & JWT token


## Using JWT Token
- Attach token as into subsequent POST/GET/PUT as header
- Key: Authorization, Value: Bearer (token)
- Example token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjEiLCJleHAiOjE2NjQ1ODc1MTcsImlhdCI6MTY2NDUwMTExNywidXNlcm5hbWUiOiJ0ZXN0LXJlZ2lzdGVyIn0.baOxL3buF2G0EtFuyo9O2FVsADqnysk9G8V25O47ejENqlY-jMb_Qj4pGIVTQYyrBBqu21C3NxzkxENk-JZa2A
