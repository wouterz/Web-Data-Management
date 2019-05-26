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
All requests are mapped through the api gateway at localhost:8762,


The message exchange tries to connect the exchange server hosted at localhost:5672. To prevent errors when starting run a rabbitMQ server, for example using docker:

`docker run -d --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq:management`

This also provides an interface of the exchange at localhost:15672 with credentials guest:guest.

