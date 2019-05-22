package service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.channel.DirectChannel;

@SpringBootApplication
@EnableEurekaClient
@EnableBinding(Processor.class)
public class UserApplication {

    @Value("${spring.application.name}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);
    private ObjectMapper mapper = new ObjectMapper();


    private DirectChannel incomingChannel;

    @Autowired
    private UserService service;


    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @StreamListener(Processor.INPUT)
    public void receiveOrder(User order) throws JsonProcessingException {
        LOGGER.info("Order received: {}", mapper.writeValueAsString(order));
        service.process(order);
    }


}
