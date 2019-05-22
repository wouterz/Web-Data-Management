package service.user.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.user.models.User;
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

    public void process(final User user) throws JsonProcessingException {
        LOGGER.info("Order processed: {}", mapper.writeValueAsString(user));
//        for (Long productId : usert.getId()) {
//            User user2 = userLocalRepository.get(user.getId());

//            LOGGER.info("Product updated: {}", mapper.writeValueAsString(user));
//        }

        LOGGER.info("Order response sent: {}", mapper.writeValueAsString(Collections.singletonMap("status", "")));
        userSender.send(user);
    }


}
