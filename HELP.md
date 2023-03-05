# Getting Started

## Reference Documentation

Check help in folder "containers" on how to start with required infrastructure

There are 3 parts in this project

### Web

Entry web api point for creating, reading, updating, deleting and promoting assets
On receive of promotion request it will just send message about promotion request to message broker (Pulsar)  

Sample http requests

* #### Send promotion request
```
POST http://localhost:8083/api/asset/promotion
Content-Type: application/json

{
"assetId": {assetId}
}
```

* #### Create asset
```
POST http://localhost:8083/api/asset
Content-Type: application/json

{
"assetDescription": "<description of new asset>"
}
```

* #### Get asset
```
GET http://localhost:8083/api/asset/{assetId}
```

* #### Delete asset
DELETE http://localhost:8083/api/asset/{assetId}

* #### Update asset
PUT http://localhost:8083/api/asset
Content-Type: application/json

{
"id": {assetId},
"description": "<new description>>"
}

* #### Create composition
POST http://localhost:8083/api/asset/composition
Content-Type: application/json

{
"parentAsset": {parentAssetId},
"childAsset": {childAssetId}
}

### Primary

Intermediate process which gets initial message with promotion request and can do some checks and temporarily suspend.
If all conditions are met then it send message to other topic for actual promotion.

### Worker

This process actually does promotion of assets (reads message from message broker)
