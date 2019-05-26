from locust import HttpLocust, TaskSet, task
import random

class UserBehavior(TaskSet):
    userid = None
    orderid = None
    items = []

    def on_start(self):
        """ This function is called before any of the defined tasks is scheduled
        It should create a new user with a random amount of credit
        and save the ID for future requests
        Should be updated to reflect how the ID is returned! """
        response = self.client.post("/users/create")
        self.userid = response.text
        self.client.post("/users/credit/add", {"user_id": self.userid, "amount": random.randint(1,1000)})

        # Make sure that we have an order
        response = self.client.post("/orders/create/" + self.userid)
        self.order = response.text

        # Make sure that at least one item exists
        response = self.client.post("/stock/item/create")
        self.items.append(response.text)

    def on_stop(self):
        """ This function is called when the taskset is stopping """
        self.client.delete("/users/remove/" + self.userid)

    def randomItemIndex():
        return random.randint(0, len(self.items)-1)


    """ List of possible tasks """

    """ User service """
    @task
    def finduser(self):
        self.client.get("/users/find/" + self.userid)

    @task
    def findcredit(self):
        self.client.get("/users/credit/" + self.userid)

    """ Order service """
    @task
    def createneworder(self):
        self.client.delete("/orders/remove/" + self.orderid)
        response = self.client.post("/orders/create/" + self.userid)
        self.orderid = response.text  
        
    @task
    def findorder(self):
        self.client.get("/orders/find/" + self.orderid)

    @task
    def additem(self):
        index = randomItemIndex()
        self.client.post("/orders/addItem", {"order_id": self.orderid, "item_id": self.items[index]})

    
