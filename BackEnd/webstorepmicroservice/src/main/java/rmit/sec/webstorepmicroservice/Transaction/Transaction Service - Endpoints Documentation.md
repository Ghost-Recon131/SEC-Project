# Transaction Service - Endpoint Documentation

Current Domain is localhost: http://localhost:8080/
# Transaction Service
All endpoints in this class requires a valid JWT token.

## Get a transaction by transaction ID
* Request Method: GET
* Endpoint URI: /api/transactions/getTransactionByID
* Requires param of "sessionID" ie: URI+?sessionID=3
```json
{
    "transactionID": 1,
    "sellerID": "NULL OR PLACEHOLDER VALUE",
    "buyerID": "NULL OR PLACEHOLDER VALUE"
}
```
* Returns single encrypted JSON object


## Get all purchases for a user
* Request Method: GET
* Endpoint URI: /api/transactions/getAllPurchases
* Requires param of "sessionID" ie: URI+?sessionID=3
* Returns arraylist of encrypted JSON objects


## Get all sales for a user
* Request Method: GET
* Endpoint URI: /api/transactions/getAllSales
* Requires param of "sessionID" ie: URI+?sessionID=3
* Returns arraylist of encrypted JSON objects


## Save a transaction
* Request Method: POST
* Endpoint URI: /api/transactions/saveTransaction
* Requires param of "sessionID" ie: URI+?sessionID=3
```json
{
    "transactionID": "NULL OR PLACEHOLDER VALUE",
    "sellerID": 1,
    "buyerID": 1,
    "totalCost": 11.11
}
```
* Returns encrypted string for result