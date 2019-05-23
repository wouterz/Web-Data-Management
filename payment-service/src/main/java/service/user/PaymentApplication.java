package service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import service.user.messaging.PaymentService;
import service.user.models.User;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Processor.class)
@EnableFeignClients
public class PaymentApplication {

    @Value("${spring.application.name}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentApplication.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PaymentService service;


    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @StreamListener(Processor.INPUT)
    public void receiveOrder(User user) throws JsonProcessingException {
        LOGGER.info("Message received: {}", mapper.writeValueAsString(user));
        service.process(user);
    }


}
