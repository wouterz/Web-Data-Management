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
import service.payment.storage.PostgresRepository;
import service.payment.storage.RedisRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    public String create(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "order_id") String order_id) {
//        LOGGER.info("Request: /payment/pay/ user " + user_id + "/ order " + order_id);
//
        Order order = (Order)orderClient.orderFind(order_id);
        int totalCost = order.getItems().size();
//        subtract credits
        boolean subtractSuccess = userClient.subtractCredits(user_id, totalCost);

        if (subtractSuccess) {
            Payment payment = new Payment(Payment.PaymentStatus.PAID, user_id, order_id, totalCost);
            return ((Payment)localRepository.create(payment)).getId();
        } else {
            return "-1";
        }

    }


    @PostMapping("/payment/cancel/{payment_id}/{user_id}")
    public String cancel(@PathVariable(value = "payment_id") String payment_id, @PathVariable(value = "user_id") String user_id) {
        LOGGER.info("Request: /payment/cancel/ payment " + payment_id + "/ user " + user_id);

        //  Reverse of create
        Payment payment = (Payment)localRepository.get(payment_id);
        if(userClient.addCredits(user_id, payment.getCredits())) {
            localRepository.delete(payment.getId());
            return "1";
        } else {
            return "-1";
        }
    }

    @GetMapping("/payment/status/{order_id}")
    public Payment.PaymentStatus status(@PathVariable(value = "order_id") String order_id) {
        LOGGER.info("Request: /payment/status/order " + order_id);

//        TODO get by orderID --> OrderId is not enough to find a payment right?
        return ((Payment)localRepository.get(order_id)).getPaymentStatus();
    }

    @GetMapping("/payment")
    public String endpoint() {

        return "This is the payment service";
    }


    @GetMapping("/payment/order/{orderId}")
    public Order orderFind(@PathVariable String orderId) {
        return orderClient.orderFind(orderId);
    }
    

}
