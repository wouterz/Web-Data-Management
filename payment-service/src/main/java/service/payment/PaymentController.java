package service.payment;

import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.payment.RMI.OrderClient;
import service.payment.RMI.UserClient;
import service.payment.models.Order;
import service.payment.models.Payment;
import service.payment.storage.Dao;
import service.payment.storage.RedisRepository;

@RestController
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);


    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private RedisRepository localRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderClient orderClient;


    @PostMapping("/payment/pay/{user_id}/{order_id}")
    public String create(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "order_id") long order_id) {
        LOGGER.info("Request: /payment/pay/ user " + user_id + "/ order " + order_id);

//        TODO
//        totalCost = orderClient.
        int totalCost = 20;

//        subtract credits
        if (userClient.subtractCredits(user_id, totalCost)) {
            return localRepository.create(counter.getAndIncrement());
        } else {
            return "-1";
        }


    }


    @PostMapping("/payment/cancel/{user_id}/{order_id}")
    public String cancel(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "order_id") long order_id) {
        LOGGER.info("Request: /payment/cancel/ user " + user_id + "/ order " + order_id);

//        TODO - reverse of create
        //        TODO get by orderID
        Payment payment = (Payment)localRepository.get(order_id);
        boolean creditSucces = userClient.addCredits(user_id, payment.getCredits());


        return localRepository.create(counter.get());
    }

    @GetMapping("/payment/status/{order_id}")
    public Payment.PaymentStatus status(@PathVariable(value = "order_id") long order_id) {
        LOGGER.info("Request: /payment/status/order " + order_id);

//        TODO get by orderID
        return ((Payment)localRepository.get(order_id)).getPaymentStatus();
    }

    @GetMapping("/payment")
    public String endpoint() {

        return "This is the payment service";
    }
    

}
