package service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserApplication {

    @Value("${spring.application.name}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);
    private ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }


}
