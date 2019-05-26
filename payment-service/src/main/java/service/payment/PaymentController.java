package service.payment;

import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.payment.messaging.PaymentSender;
import service.payment.storage.LocalRepository;

@RestController
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);


    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private PaymentSender sender;


    @PostMapping("/payment/pay/{user_id}/{order_id}")
    public long create(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "order_id") long order_id) {
        LOGGER.info("Request: /payment/pay/ user " + user_id + "/ order " + order_id);

        return localRepository.create(counter.get());
    }


    @PostMapping("/payment/cancel/{user_id}/{order_id}")
    public long cancel(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "order_id") long order_id) {
        LOGGER.info("Request: /payment/cancel/ user " + user_id + "/ order " + order_id);

        return localRepository.create(counter.get());
    }

    @GetMapping("/payment/status/{order_id}")
    public long status(@PathVariable(value = "order_id") long order_id) {
        LOGGER.info("Request: /payment/status/order " + order_id);

        return localRepository.create(counter.get());
    }

}
