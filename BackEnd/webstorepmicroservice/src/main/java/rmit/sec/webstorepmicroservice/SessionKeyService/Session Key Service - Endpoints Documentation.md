# Session Key Service - Endpoint Documentation

Current Domain is localhost: http://localhost:8080/
# Session Key Service
## Get server RSA public key
* Request Method: GET
* Endpoint URI: /api/sessionKeyService/getServerPublicKey
* Returns the key as a string value


## Initial Key Exchange
* Request Method: POST
* Endpoint URI: /api/sessionKeyService/keyExchange
* Send as a "EncryptedDataRequest" but the session ID can be set to any valid Long as it is not used in the backend
* The "encryptedData" field needs to be the ciphertext from encrypting the session key with the sever's public key
```json
{
    "sessionID": "0",
    "encryptedData": "MUIFG261EdNlQySAILuYm8EbdwcPrAcFdy/tXfivwEaK50fAqnYqJ8wsB0gSlupR6kqpu7ztHbnDJKSuekaty7TZ58M6RBeAX670DlR6uua4herZbbqUxprDXJqJ7v5V+cKfRipygt4JDc4LsY95sUZyg6WL8i1gZy/fFWonHcCm8hhYIofebT6K4Voubo/DZP/c1uaPruPGK4q7SSrmM1TAtnpc46QeqLZWeX5Izqi9dmRp3xBfXZsryGUS/5JCCeIqWGOXrlRidecJWx/NyNtnJUEFO1NiGpGNJ9OvfhfhL9Bvk/PfW5u41fUhPSrU218l7YwQbzI0WO+rE8ViFQ=="
}
```
* Returns the sessionID on successful key exchange


## TEST ENDPOINT: AES ENCRYPT
* Request Method: POST
* sessionID value can be invalid or 0
* Endpoint URI: /api/sessionKeyService/aesEncrypt
```json
{
    "sessionID": "3",
    "encryptedData": "test123456"
}
```
* Returns the encrypted data as a string


## TEST ENDPOINT: AES DECRYPT
* Request Method: GET
* Endpoint URI: api/sessionKeyService/aesDecrypt
* Valid sessionID needed
```json
{
    "sessionID": "3",
    "encryptedData": "jP+V3/QE57lkcX44FYPo7Q=="
}
```
* Returns the decrypted data as a string


## TEST ENDPOINT: RSA ENCRYPT
* Request Method: POST
* Endpoint URI: api/sessionKeyService/rsaEncrypt
* sessionID value can be invalid or 0
```json
{
    "sessionID": "1",
    "encryptedData": "UD+4bySKcZw-J*dg"
}
```
* Returns the encrypted data as a string


## TEST ENDPOINT: RSA DECRYPT
* Request Method: GET
* Endpoint URI: api/sessionKeyService/rsaDecrypt
* sessionID value can be invalid or 0
```json
{
    "sessionID": "1",
    "encryptedData": "B88WMSxD7duedP4CwlcI/6X2btPStY3H+UMKeAWyNtjPK8xLamJi+M1IkMjPWAXsoLFDdUXQq0Fe8IvxN3adehayAXu+2vG2Qg7jnmsDo50J5Ccw4N60ti83v/9hGGREpCm51CpkUU4TvapnpFbTL3YT7HavwgkSTS36BEF/27jKk6saq6K9tTWxRmm2tZAcszLpDsRM19m81XsECgo4A31t185kWcBwKX7PvhK3BeKrZeQ2PY766R3sX70oV/p6FqaA1Y1f0yeZv/8y/WQRMgp6Hf6hiOSrt8KKH4WVLOAadUOFQPQ/dIF2+1RzxDMyOIjSqX4JdqcOm7BOjL0Eqg=="
}
```
* Returns the encrypted data as a string