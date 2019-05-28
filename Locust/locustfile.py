from locust import HttpLocust, TaskSet, task
import random

class Order:
    # Constructor
    def __init__(self, userid, orderid):
        self.userid = userid
        self.orderid = orderid
        self.items = []
        self.paid = False

    def addItem(self, itemid):
        self.items.append(itemid)

    def setPaid(self, bool):
        self.paid = bool

class UserBehavior(TaskSet):

    def on_start(self):
        """ This function is called before any of the defined tasks is scheduled
        It should create a new user with a random amount of credit
        and save the userid for future requests """

        userResponse = self.client.post("/users/create")
        self.userid = userResponse.text
        self.client.post("/users/credit/add", {"user_id": self.userid, "amount": random.randint(1,1000)})

        # Create a list of orders for this user
        self.orders = []

        # Create an initial order
        response = self.client.post("/orders/create/", {"user_id": self.userid})
        self.orders.append(Order(self.userid, response.text))

        # Make sure that at least one item exists
        response = self.client.post("/stock/item/create")
        self.orders[0].addItem(response.text)

    def on_stop(self):
        """ This function is called when the taskset is stopping """
        self.client.delete("/users/remove/" + self.userid)

    
    """ List of possible user tasks, this should roughly emulate how a
    normal user would use the system, creating new orders, adding items to orders
    and paying for / cancelling payments for orders. """

    """ Users service """
    # Find user details (for the current user)
    @task
    def findCurrentUser(self):
        self.client.get("/users/find/" + self.userid)

    # Find credit (for the current user)
    @task
    def findCurrentCredit(self):
        self.client.get("/users/credit/" + self.userid)

    # subtract credit should probably not be called without actually paying for an order
    # thus this endpoint is not called directly by the locust client
    
    # Add some random amount of credit to the account
    @task
    def addCredit(self):
        self.client.post("/users/credit/add", {"user_id": self.userid, "amount": random.randint(1,100)})

class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait = 1000
    max_wait = 10000




    
    

    
