package service.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StockApplication {


    @Value("${spring.application.name}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(StockApplication.class);
    private ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }



}
