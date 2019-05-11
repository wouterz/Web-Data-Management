package main.java.order;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

@RestController
// Catch everything starting with /orders
@RequestMapping("/orders")
public class OrderController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    private Jedis jedis = new Jedis("localhost");

    // @RequestMapping(method=GET) or POST etc. to narrow mapping. without method=.. it maps all HTTP ops.
    // Pathvariable => straight up
    @RequestMapping("/create/{userId}")
    public Order orderCreate(@PathVariable String userId) {
        Order o = new Order(userId, counter.incrementAndGet());
        jedis.set(o.getorderId(), o.getUserId());
        return o;
    }


    // @RequestMapping("/remove/{orderid}")
    // RequestParam => orders/remove?orderId={id}
    @RequestMapping("/remove")
    public boolean orderRemove(@RequestParam(value="orderId", defaultValue="test") String orderId) {
        long deleted = jedis.del(orderId);
        if (deleted>0){
            return true;
        }
        return false;
    }


    @RequestMapping("/find/{orderid}")
    public Order orderFind(@RequestParam(value="name", defaultValue="World") String name) {
        return new Order("def", counter.incrementAndGet());
    }

    @RequestMapping("/addItem/{orderid}/{itemid}")
    public Order orderAddItem(@RequestParam(value="name", defaultValue="World") String name) {
        return new Order("def", counter.incrementAndGet());
    }

    @RequestMapping("/removeItem/{orderid}/{itemid}")
    public Order orderRemoveItem(@RequestParam(value="name", defaultValue="World") String name) {
        return new Order("def", counter.incrementAndGet());
    }

    @RequestMapping("/checkout/{orderid}")
    public Order orderCheckout(@RequestParam(value="name", defaultValue="World") String name) {
        return new Order("def", counter.incrementAndGet());
    }

}