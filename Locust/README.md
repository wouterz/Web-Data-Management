# User behaviour
The user behaviour of individual users is defined in the ```locustfile.py```, calls to every endpoint are made. Some checks should be changed before testing on our actual gateway, please read to code comments to find out where.

In addition, each call to the API endpoint has an associated weight in the task annotation, a task with weight 10 is ten times more likely to be executed by a single user than a task with weight 1. I've tried to balance the weights, however if we see some problems (like items getting out of stock, or users running out of credit) then these weights need to be tweaked. This can be done by simply changing the int value in the annotation
# Running locust
To run locust, make sure that your command line is in the same directory as the ```locustfile.py``` and then simply run ```locust --host=[GATEWAY_IP]```.

The locust interface will then we accessible at http://localhost:8089/, use that interface to specify the amount of users and spawn rate to use for testing.