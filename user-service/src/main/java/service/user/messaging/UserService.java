package service.user.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.user.storage.LocalRepository;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    LocalRepository localRepository;

    @Autowired
    UserSender userSender;

    public void process(final Object o) throws JsonProcessingException {
        LOGGER.info("Order processed: {}", mapper.writeValueAsString(o));
//        for (Long productId : usert.getId()) {
//            User user2 = userLocalRepository.get(user.getId());

//            LOGGER.info("Product updated: {}", mapper.writeValueAsString(user));
//        }
    }


}
