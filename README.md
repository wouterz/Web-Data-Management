# Web-Data-Management

## How to run
Run all services
* Eureka (discovery)
* Zuul (gateway)
* Order-service
* User-service
* Stock-service
* Payment-service

Eureka discovery service provides an interface at localhost:8761. All requests are mapped through the zuul api gateway at localhost:8762.

```
./gradlew -p user-service build && ./gradlew -p stock-service build && ./gradlew -p order-service build
  && ./gradlew -p payment-service build && ./gradlew -p eureka-server build && ./gradlew -p zuul-server build
```
```
docker-compose -f docker-compose-postgres.yml build
```
```
docker-compose -f docker-compose-postgres.yml up -d
```

## Users service
`/user/create/`<br>
POST - returns an ID<br>

`/user/remove/{user_id}`<br>
DELETE - return success/failure<br>

`/user/{user_id}`<br>
GET - returns a set of users with their details (id, and credit)<br>

`/user/{user_id}/credit`<br>
GET - returns the current credit of a user<br>

`/user/{user_id}/credit/subtract/{amount}`<br>
POST - subtracts the amount from the credit of the user (e.g., to buy an order). Returns success or failure, depending on the credit status. <br>

`/user/{user_id}/credit/add/{amount}`<br>
POST - subtracts the amount from the credit of the user. Returns success or failure, depending on the credit status. 

`/user`<br>
GET - Healthcheck

## Order Service
`/order/create/{user_id}`<br>
POST - creates an order for the given user, and returns an order_id<br>

`/order/remove/{order_id}`<br>
DELETE - deletes an order by ID<br>

`/order/find/{order_id}`<br>
GET - retrieves the information of an order (payment status, items included and user id)<br>

`/order/addItem/{order_id}/{item_id}`<br>
POST - adds a given item in the order given<br>

`/order/removeItem/{order_id}/{item_id}`<br>
DELETE - removes the given item from the given order<br>

`/order/checkout/{order_id}`<br>
POST - makes the payment (via calling the payment service), subtracts the stock (via the stock service) and return a status (success/failure).<br>

`/order`<br>
GET - Healthcheck<br>

## Stock Service
`/stock/availability/{item_id}`<br>
GET - returns an item’s availability.<br>

`/stock/subtract/{item_id}/{number}`<br>
POST - subtracts an item from stock by the amount specified.<br>

`/stock/add/{item_id}/{number}`<br>
POST - adds the given number of stock items to the item count in the stock<br>

`/stock/item/create`<br>
POST - adds an item, and returns its ID.<br>

`/stock`<br>
GET - Healthcheck<br>

## Payment Service
`/payment/pay/{user_id}/{order_id}`<br>
POST - subtracts the amount of the order from the user’s credit (returns failure if credit is not enough)<br>

`/payment/cancel/{user_id}/{order_id}`<br>
POST - cancels payment made by a specific user for a specific order.<br>

`/payment/status/{order_id}`<br>
GET - returns the status of the payment (paid or not)<br>

`/payment`<br>
GET - Healthcheck<br>
