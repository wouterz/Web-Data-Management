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

    def removeItem(self, itemIndex):
        self.items.pop(itemIndex)

    def setPaid(self, bool):
        self.paid = bool

class UserBehavior(TaskSet):

    def on_start(self):
        self.client.post("/user/create")
    
    def on_stop(self):
        print("Finished")

    @task
    def createNewUser(self):
        self.client.post("/user/create")
    
class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait = 1000
    max_wait = 10000




    
    

    
