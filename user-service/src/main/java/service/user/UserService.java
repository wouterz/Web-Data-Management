package service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import service.user.storage.UserLocalRepository;

import java.util.Collections;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserLocalRepository userLocalRepository;

    @Autowired
    UserSender userSender;

    public void process(final User usert) throws JsonProcessingException {
        LOGGER.info("Order processed: {}", mapper.writeValueAsString(usert));
//        for (Long productId : usert.getId()) {
            User user = userLocalRepository.get(usert.getId());

            LOGGER.info("Product updated: {}", mapper.writeValueAsString(user));
//        }

        LOGGER.info("Order response sent: {}", mapper.writeValueAsString(Collections.singletonMap("status", "")));
        userSender.send(usert);
    }


}
