# Accounts - Endpoint Documentation

Current Domain is localhost: http://localhost:8080/
# Accounts
## Register account
* Request Method: POST
* Endpoint URI: /api/RegisterLogin/register?sessionID=
* Takes a param of "sessionID" as a Long
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
* Returns a string message for outcome of registration


## Login
- Request Method: POST
- Endpoint URI: /api/RegisterLogin/login?sessionID=
- Takes a param of "sessionID" as a Long
```json
{
  "username": "test-register",
  "password": "test123"
}
```
* Returns result of action & JWT token


## Using JWT Token
- Attach token as into subsequent POST/GET/PUT as header
- Key: Authorization, Value: Bearer (token)
- Example token: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjEiLCJleHAiOjE2NjQ1ODc1MTcsImlhdCI6MTY2NDUwMTExNywidXNlcm5hbWUiOiJ0ZXN0LXJlZ2lzdGVyIn0.baOxL3buF2G0EtFuyo9O2FVsADqnysk9G8V25O47ejENqlY-jMb_Qj4pGIVTQYyrBBqu21C3NxzkxENk-JZa2A


## Forgot Password
* Request Method: POST
* Endpoint URI: /api/RegisterLogin/forgotPassword?sessionID=
* Takes a param of "sessionID" as a Long
```json
{
  "username": "test-register",
  "secret_question": "secret_question",
  "secret_question_answer": "secret_question_answer",
  "newPassword": "qwerty123"
}
```
* Returns a string for result of password change attempt


## Viewing Account Information
* Request Method: GET
* Endpoint URI: api/authorised/viewAccountInfo?sessionID=
* JWT Token must be present in header
* Takes a param of "sessionID" as a Long
* Returns a JSON object with various fields


## Change Password
* Request Method: POST
* Endpoint URI: api/authorised/viewAccountInfo?sessionID=
* JWT Token must be present in header
* Takes a param of "sessionID" as a Long
```json
{
  "password": "password"
}
```
* Returns a string message for result of action


## Update account information
* Request Method: PUT
* Endpoint URI: api/authorised/updateAccountInfo?sessionID=
* JWT Token must be present in header
* Takes a param of "sessionID" as a Long
```json
{
  "email": "email",
  "firstname": "firstname",
  "lastname": "lastname"
}
```
* Returns a string message for result of action