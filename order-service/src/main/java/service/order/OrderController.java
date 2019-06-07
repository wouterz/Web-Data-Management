package service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.order.RMI.PaymentClient;
import service.order.RMI.StockClient;
import service.order.models.Order;
import service.order.models.StockItem;
import service.order.storage.Dao;

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
    Dao<Order> localRepository;

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
    public long orderCreate(@PathVariable(value = "userId") long userId) {
        return localRepository.create(userId);
    }


    // @RequestMapping("/remove/{orderid}")
    // RequestParam => orders/remove?orderId={id}
    // DELETE - deletes order with orderId
    // Return success/failure ?
    @DeleteMapping("/remove/{orderId}")
    public boolean orderRemove(@PathVariable(value = "orderId") long orderId) {
        return localRepository.delete(orderId);
    }


    @GetMapping("/find/orderId")
    // GET - retrieve info about orderId: pay status, items, userId
    public Order orderFind(@PathVariable long orderId) {
        return localRepository.get(orderId);
    }

    @PostMapping("/addItem/{orderId}/{itemId}")
    // POST - add item with itemId to orderId
    public boolean orderAddItem(@PathVariable(value = "orderId") long orderId, @PathVariable(value = "itemId") long itemId) {
        // get order - orderId
        Order o = localRepository.get(orderId);

        return localRepository.update(orderId, o.addItem(itemId));
    }

    @DeleteMapping("/removeItem/{orderId}/{itemId}")
    // DELETE/POST - remove itemId from orderId
    public boolean orderRemoveItem(@PathVariable(value = "orderId") long orderId, @PathVariable(value = "itemId") long itemId) {
        // get order - orderId
        Order o = localRepository.get(orderId);
        // remove item:itemId from order
        return localRepository.update(orderId, o.removeItem(itemId));
    }

    // POST - make payment (call pay service), subtract stock(stock service)
    //          return status (success/fail)
    @PostMapping("/checkout/{orderId}")
    public boolean orderCheckout(@PathVariable(value = "orderId") long orderId) {
        // get order - orderId
        Order o = localRepository.get(orderId);

        // checkout order
        List<Long> subtractedItems = new ArrayList<>();
        for (long item : o.getItems()) {
            StockItem updatedStock = stockClient.subtractStock(item, 1);
            if (updatedStock != null) {
                subtractedItems.add(updatedStock.getId());
            } else {
                for (long toReset : subtractedItems) {
                    StockItem resetItem =  stockClient.addStock(toReset, 1);
                    if (resetItem == null) {
                        throw new RuntimeException("could not reset db, inconsistent");
                    }
                }
                return false;
            }
        }

        return true;
    }

    // POST - make payment (call pay service), subtract stock(stock service)
    //          return status (success/fail)
    @PostMapping("/cancelcheckout/{orderId}")
    public boolean cancelOrderCheckout(@PathVariable(value = "orderId") long orderId) {
        // get order - orderId
        Order o = localRepository.get(orderId);

        // undo checkout order
        List<Long> subtractedItems = new ArrayList<>();
        for (long item : o.getItems()) {
            StockItem updatedStock = stockClient.addStock(item, 1);
            if (updatedStock == null) {
                return false;
            }
        }

        return true;
    }

    // POST - make payment (call pay service), subtract stock(stock service)
    //          return status (success/fail)
    @GetMapping("/price/{orderId}")
    public int getPrice(@PathVariable(value = "orderId") long orderId) {
        // get order - orderId
        Order o = localRepository.get(orderId);

        return o.getItems().size();
    }

}