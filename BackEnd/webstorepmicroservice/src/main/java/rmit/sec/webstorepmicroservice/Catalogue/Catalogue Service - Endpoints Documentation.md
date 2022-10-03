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
* Requires param of "sessionID" AND "sellerID" ie: URI+?sessionID=3&sellerID=3
* Returns arraylist of encrypted JSON objects


###  Get all items by a particular category
* Request Method: GET
* Endpoint URI: /api/catalogue/allItemsBySeller
* Requires param of "sessionID" AND "sellerID" ie: URI+?sessionID=3&sellerID=3
* Returns arraylist of encrypted JSON objects


### Get all Available items
* Request Method: GET
* Endpoint URI: /api/catalogue/allItemsByCategory
* Requires param of "sessionID" AND "category" ie: URI+?sessionID=3&category=ELECTRONICS
* Returns arraylist of encrypted JSON objects


## Get all available items
* Request Method: GET
* Endpoint URI: /api/catalogue/allAvailableItems
* Requires param of "sessionID"
* Returns arraylist of encrypted JSON objects


## Get Product By ID
* Request Method: GET
* Endpoint URI: /api/catalogue/allAvailableItems
* Requires param of "sessionID" AND "itemID" ie: URI+?sessionID=3&itemID=1
* Returns single JSON object


## List Product



## Update Product



