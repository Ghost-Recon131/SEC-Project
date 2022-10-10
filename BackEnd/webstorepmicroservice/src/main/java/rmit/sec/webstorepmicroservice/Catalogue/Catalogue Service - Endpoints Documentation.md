# Catalogue Service - Endpoint Documentation

Current Domain is localhost: http://localhost:8080/
# Catalogue Service
## Fetching items from backend
### Get All Products
* Request Method: GET
* Endpoint URI: /api/catalogue/allItems
* Requires param of "sessionID" ie: URI+?sessionID=3
* Returns arraylist of encrypted JSON objects


### Get all items by a particular seller
* Request Method: GET
* Endpoint URI: /api/catalogue/allItemsBySeller
* Requires param of "sessionID"
```json
{
  "itemID": "NULL OR ANY PLACEHOLDER VALUE", 
  "sellerID": "valid Long",
  "category": "NULL OR ANY PLACEHOLDER VALUE"
}
```
* Returns arraylist of encrypted JSON objects


###  Get all items by a particular category
* Request Method: GET
* Endpoint URI: /api/catalogue/allItemsBySeller
* Requires param of "sessionID"
```json
{
  "itemID": "NULL OR ANY PLACEHOLDER VALUE", 
  "sellerID": "NULL OR ANY PLACEHOLDER VALUE",
  "category": "Refer to ItemCategory.java for all categories"
}
```
* Returns arraylist of encrypted JSON objects


### Get all Available items
* Request Method: GET
* Endpoint URI: /api/catalogue/allItemsByCategory
* Requires param of "sessionID"
* Returns arraylist of encrypted JSON objects


## Get Product By ID
* Request Method: POST
* Endpoint URI: /api/catalogue/viewItem
* Requires param of "sessionID"
```json
{
  "itemID": "valid Long", 
  "sellerID": "NULL OR ANY PLACEHOLDER VALUE",
  "category": "NULL OR ANY PLACEHOLDER VALUE"
}
```
* Returns single JSON object


## List item
* Request Method: POST
* Endpoint URI: /api/authorised/catalogue/listItem
* Requires valid JWT token in header
* Requires param of "sessionID"
```json
{
  "itemID": "NULL OR ANY PLACEHOLDER VALUE",
  "itemName": "String",
  "itemDescription": "String",
  "itemAvailable": "NULL",
  "itemPrice": "Double",
  "itemQuantity": "Integer",
  "itemImage": "String",
  "category": "Refer to ItemCategory.java for all categories"
}
```
* Returns string message of success or failure


## Edit item
* Request Method: POST
* Endpoint URI: /api/authorised/catalogue/editItem
* Requires valid JWT token in header
* Requires param of "sessionID"
* For 'itemAvailable', send a value of 0 for false, 1 for true
```json
{
  "itemID": "valid Long",
  "itemName": "String",
  "itemDescription": "String",
  "itemAvailable": "NULL OR 0 or 1",
  "itemPrice": "Double",
  "itemQuantity": "Integer",
  "itemImage": "String",
  "category": "Refer to ItemCategory.java for all categories"
}
```
* Returns string message of success or failure


## End item listing
* Request Method: POST
* Endpoint URI: /api/authorised/catalogue/
* Requires valid JWT token in header
* Requires param of "sessionID"
```json
{
  "itemID": "valid Long",
  "sellerID": "NULL OR ANY PLACEHOLDER VALUE",
  "category": "NULL OR ANY PLACEHOLDER VALUE"
}
```
* Returns string message of success or failure


