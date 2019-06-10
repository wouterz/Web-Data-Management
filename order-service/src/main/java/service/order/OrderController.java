package service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.order.RMI.PaymentClient;
import service.order.RMI.StockClient;
import service.order.models.Order;
import service.order.models.StockItem;
import service.order.storage.Dao;
import service.order.storage.RedisRepository;
import service.order.storage.PostgresRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
// Catch everything starting with /order
@RequestMapping("/order")
public class OrderController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private
    RedisRepository localRepository;

    @Autowired
    private
    StockClient stockClient;

    @Autowired
    private
    PaymentClient paymentClient;

    // @RequestMapping(method=GET) or POST etc. to narrow mapping. without method=.. it maps all HTTP ops.
    // Pathvariable => straight up
    // POST - create order for userId, return orderId
    @PostMapping("/create/{userId}")
    public String orderCreate(@PathVariable(value = "userId") String userId) {
        return (localRepository.create(userId)).getOrderId();
    }


    // @RequestMapping("/remove/{orderid}")
    // RequestParam => orders/remove?orderId={id}
    // DELETE - deletes order with orderId
    // Return success/failure ?
    @DeleteMapping("/remove/{orderId}")
    public boolean orderRemove(@PathVariable(value = "orderId") String orderId) {
        return localRepository.delete(orderId);
    }


    @GetMapping("/find/{orderId}")
    // GET - retrieve info about orderId: pay status, items, userId
    public Order orderFind(@PathVariable String orderId) {
        return (Order)localRepository.get(orderId);
    }

    @PostMapping("/addItem/{orderId}/{itemId}")
    // POST - add item with itemId to orderId
    public Order orderAddItem(@PathVariable(value = "orderId") String orderId, @PathVariable(value = "itemId") String itemId) {
        // get order - orderId
        Order o = localRepository.get(orderId);
        o.addItem(itemId);

        return localRepository.update(o);
    }

    @DeleteMapping("/removeItem/{orderId}/{itemId}")
    // DELETE/POST - remove itemId from orderId
    public Order orderRemoveItem(@PathVariable(value = "orderId") String orderId, @PathVariable(value = "itemId") String itemId) {
        // get order - orderId
        Order o = localRepository.get(orderId);
        // remove item:itemId from order
        o.removeItem(itemId);
        return localRepository.update(o);
    }

    // POST - make payment (call pay service), subtract stock(stock service)
    //          return status (success/fail)
    @PostMapping("/checkout/{orderId}")
    public String orderCheckout(@PathVariable(value = "orderId") String orderId) {
        // get order - orderId
        Order o = localRepository.get(orderId);

        
        // checkout order
        String paymentId = paymentClient.create(o.getUserId(), orderId);
        // payment failed
        if (paymentId.equals("-1") ) {
            return "payment create fail";
        }
        // checkout order
        List<String> subtractedItems = new ArrayList<>();
        for (String item : o.getItems()) {
            StockItem updatedStock = stockClient.subtractStock(item, 1);
            if (updatedStock != null) {
                subtractedItems.add(updatedStock.getId());
            } else {
                paymentClient.cancel(paymentId, o.getUserId());
                for (String toReset : subtractedItems) {
                    StockItem resetItem =  stockClient.addStock(toReset, 1);
                    if (resetItem == null) {
                        throw new RuntimeException("could not reset db, inconsistent");
                    }
                }
                return paymentId;
            }
        }

        o.setPayed(true);
        Order updated = localRepository.update(o);
        if (updated == null) {
            paymentClient.cancel(paymentId, o.getUserId());
            for (String toReset : subtractedItems) {
                StockItem resetItem =  stockClient.addStock(toReset, 1);
                if (resetItem == null) {
                    throw new RuntimeException("could not reset db, inconsistent");
                }
            }
            return "failed to update object";
        }

        return paymentId;
    }


    @GetMapping("")
    public String endpoint() {
        return "This is the order service";
    }


    @GetMapping("/find/item/{itemId}")
    public StockItem itemFind(@PathVariable String itemId) {
        return stockClient.findStockItem(itemId);
    }

}