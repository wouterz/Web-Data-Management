package main.java.order;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

@RestController
public class OrderController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    private Jedis jedis = new Jedis("localhost");

    // @RequestMapping(method=GET) or POST etc. to narrow mapping. without method=.. it maps all HTTP ops.
    @RequestMapping("/orders")
    public Order order(@RequestParam(value="name", defaultValue="World") String name) {
        return new Order(counter.incrementAndGet(),
                            String.format(template, name));
    }


    @RequestMapping("/greeting/runar")
    public Order greetingrunar(@RequestParam(value="name", defaultValue="World") String name) {
        jedis.set("runar", "wat");
        String ca = jedis.get("/greeting/runar");
        System.out.println(ca);
        return new Order(counter.incrementAndGet(),
                            String.format(template, name));
    }

}