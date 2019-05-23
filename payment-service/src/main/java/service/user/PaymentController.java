package service.user;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.user.messaging.PaymentSender;
import service.user.models.User;
import service.user.storage.PaymentLocalRepository;

@RestController
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);


    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private PaymentLocalRepository paymentLocalRepository;

    @Autowired
    private PaymentSender sender;


    @PostMapping("/payment/create")
    public long create() {
        LOGGER.info("Request: /payment/create");

        return paymentLocalRepository.create(counter.get());
    }


}
