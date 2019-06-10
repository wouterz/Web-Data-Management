# Web-Data-Management

git a
Run all services
* Eureka (discovery)
* Zuul (gateway)
* Order-service
* User-service
* Stock-service
* Payment-service

Eureka discovery service provides an interface at localhost:8761
All requests are mapped through the api gateway at localhost:8762


# Users service
/user/create/
POST - returns an ID
/user/remove/{user_id}
DELETE - return success/failure
/user/{user_id}
GET - returns a set of users with their details (id, and credit)
/user/{user_id}/credit
GET - returns the current credit of a user
/user/{user_id}/credit/subtract/{amount}  
POST - subtracts the amount from the credit of the user (e.g., to buy an order). Returns success or failure, depending on the credit status. 
/user/{user_id}/credit/add/{amount}  
POST - subtracts the amount from the credit of the user. Returns success or failure, depending on the credit status. 
/user
GET - Healthcheck

# Order Service
/order/create/{user_id}
POST - creates an order for the given user, and returns an order_id
/order/remove/{order_id}
DELETE - deletes an order by ID
/order/find/{order_id}
GET - retrieves the information of an order (payment status, items included and user id)
/order/addItem/{order_id}/{item_id}
POST - adds a given item in the order given
/order/removeItem/{order_id}/{item_id}
DELETE - removes the given item from the given order
/order/checkout/{order_id}
POST - makes the payment (via calling the payment service), subtracts the stock (via the stock service) and return a status (success/failure).
/order
GET - Healthcheck

# Stock Service
/stock/availability/{item_id}
GET - returns an item’s availability.
/stock/subtract/{item_id}/{number}
POST - subtracts an item from stock by the amount specified.
/stock/add/{item_id}/{number}
POST - adds the given number of stock items to the item count in the stock
/stock/item/create/
POST - adds an item, and returns its ID.
/stock
GET - Healthcheck

# Payment Service
/payment/pay/{user_id}/{order_id}
POST - subtracts the amount of the order from the user’s credit (returns failure if credit is not enough)
/payment/cancel/{user_id}/{order_id}
POST - cancels payment made by a specific user for a specific order.
/payment/status/{order_id}
GET - returns the status of the payment (paid or not)
/payment
GET - Healthcheck
