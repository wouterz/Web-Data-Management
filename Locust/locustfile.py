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
        """ This function is called before any of the defined tasks is scheduled
        It should create a new user with a random amount of credit
        and save the userid for future requests """

        userResponse = self.client.post("/users/create")
        self.userid = userResponse.text
        self.client.post("/users/credit/add", {"user_id": self.userid, "amount": random.randint(1,1000)})

        # Create a list of orders for this user
        self.orders = []

        # Create an initial order
        response = self.client.post("/orders/create", {"user_id": self.userid})
        self.orders.append(Order(self.userid, response.text))

        # Make sure that at least one item exists
        response = self.client.post("/stock/item/create")
        self.orders[0].addItem(response.text)

    def on_stop(self):
        """ This function is called when the taskset is stopping """
        self.client.delete("/users/remove/" + self.userid)

    # Helper function to find random order with given status
    def findOrdersWithStatus(self, paid):
        res = []
        for order in self.orders:
            if order.paid == paid:
                res.append(order)
        return res

    
    """ List of possible user tasks, this should roughly emulate how a
    normal user would use the system, creating new orders, adding items to orders
    and paying for / cancelling payments for orders. """

    """ Users service """
    # Find user details (for the current user)
    @task(10)
    def findCurrentUser(self):
        self.client.get("/users/find/" + self.userid)

    # Find credit (for the current user)
    @task(10)
    def findCurrentCredit(self):
        self.client.get("/users/credit/" + self.userid)

    # subtract credit should probably not be called without actually paying for an order
    # thus this endpoint is not called directly by the locust client
    
    # Add some random amount of credit to the account
    @task(1)
    def addCredit(self):
        self.client.post("/users/credit/add", {"user_id": self.userid, "amount": random.randint(1,100)})

    """ Order Service """
    # Create new order for this user
    @task(5)
    def createNewOrder(self):
        response = self.client.post("/orders/create", {"user_id": self.userid})
        orderObj = Order(self.userid, response.text)
        self.orders.append(orderObj)

    # Delete an order for this user if possible
    @task(1)
    def deleteRandomOrder(self):
        if len(self.orders) > 0:
            index = random.randint(0, len(self.orders)-1)
            self.client.delete("/orders/remove/" + self.orders[index].orderid)
            self.orders.pop(index)

    # Find the details for a random order for the current user if possible
    @task(10)
    def findRandomOrder(self):
        if len(self.orders) > 0:
            index = random.randint(0, len(self.orders)-1)
            self.client.get("/orders/find/" + self.orders[index].orderid)

    # Create a new item and add it to an unpaid order
    @task(10)
    def addItemToOrder(self):
        unpaidOrders = self.findOrdersWithStatus(False)

        if len(unpaidOrders) > 0:
            randomOrder = unpaidOrders[random.randint(0, len(unpaidOrders)-1)]
            randomID = randomOrder.orderid

            response = self.client.post("/stock/item/create")
            itemID = response.text

            self.client.post("/orders/addItem", {"order_id": randomID, "item_id": itemID})
            randomOrder.addItem(itemID)

    # Remove a random item from an unpaid order
    @task(2)
    def removeRandomItemOrder(self):
        unpaidOrders = self.findOrdersWithStatus(False)

        if len(unpaidOrders) > 0:
            randomOrder = unpaidOrders[random.randint(0, len(unpaidOrders)-1)]

            if len(randomOrder.items) > 0:
                randomItemIndex = random.randint(0, len(randomOrder.items)-1)
                randomItemID = randomOrder.items[randomItemIndex]

                self.client.delete("/orders/removeItem/" + randomOrder.orderid + "/" + randomItemID)
                randomOrder.removeItem(randomItemIndex)

    # Checkout a random unpaid order
    @task(2)
    def checkoutRandomOrder(self):
        unpaidOrders = self.findOrdersWithStatus(False)

        if len(unpaidOrders) > 0:
            randomOrder = unpaidOrders[random.randint(0, len(unpaidOrders)-1)]

            self.client.post("/orders/checkout", {"order_id": randomOrder.orderid})

            # !! SHOULD CHECK IF WE ACTUALLY GOT BACK SUCCESS OR FAILURE!
            randomOrder.setPaid(True)

    """ Stock Service """
    # Get availability for a random item
    @task(10)
    def getRandomItemAvailability(self):
        if len(self.orders) > 0:
            randomOrder = self.orders[random.randint(0, len(self.orders)-1)]
            randomItem = randomOrder.items[random.randint(0, len(randomOrder.items)-1)]

            self.client.get("/stock/availability/" + randomItem)

    # Subtracting manually from stock should probabily not be called by a client

    # Clients can add stock for an item in a random unpaid item (should this be called
    # by the client?)
    @task(5)
    def addStockRandomItem(self):
        unpaidOrders = self.findOrdersWithStatus(False)

        if len(unpaidOrders) > 0:
            randomOrder = unpaidOrders[random.randint(0, len(unpaidOrders)-1)]
            randomItem = randomOrder.items[random.randint(0, len(randomOrder.items)-1)]
            stockAmount = random.randint(1,100)
            self.client.post("/stock/add", {"item_id": randomItem, "number": stockAmount})

    """ Payment Service """
    # Pay should not be called directly, is tested by checkout from orderservice

    # Cancel the payment of a specific order
    @task(1)
    def cancelRandomPaidOrder(self):
        paidOrders = self.findOrdersWithStatus(True)

        if len(paidOrders) > 0:
            randomOrder = paidOrders[random.randint(0, len(paidOrders)-1)]
            self.client.post("/payment/cancelPayment", {"user_id": self.userid, "order_id": randomOrder.orderid})
            randomOrder.setPaid(False)
    
    # Check the status of a random order
    @task(10)
    def getPaymentStatusRandomOrder(self):
        if len(self.orders) > 0:
            randomOrder = self.orders[random.randint(0, len(self.orders)-1)]
            self.client.get("/payment/status/" + randomOrder.orderid)
    
class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait = 1000
    max_wait = 10000




    
    

    
