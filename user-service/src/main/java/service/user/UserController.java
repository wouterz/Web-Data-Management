package service.user;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.user.RMI.OrderClient;
import service.user.RMI.PaymentClient;
import service.user.RMI.StockClient;
import service.user.messaging.UserSender;
import service.user.models.User;
import service.user.storage.UserLocalRepository;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private StockClient stockClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private UserSender sender;

    @Autowired
    private UserLocalRepository userLocalRepository;



    @PostMapping("/user/create")
    public long create() {
        LOGGER.info("Request: /user/create");
        User user = new User(counter.getAndIncrement(), 0);
        boolean send = sender.send(user);
        LOGGER.info("Request: Message : " + user.toString() +" was sent: " + send);


        return userLocalRepository.create(counter.get());
    }

    @DeleteMapping("/user/{user_id}")
    public boolean remove(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request: /user/remove/" + user_id);


//        EXAMPLE RMI USAGE
        stockClient.createStockItem("NewItem1");

        return userLocalRepository.delete(user_id);
    }

    @GetMapping("/user/{user_id}")
    public String find(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request: user/" + user_id);

        return userLocalRepository.get(user_id).toString();
    }

    @GetMapping("/user/{user_id}/credit")
    public long getCredits(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request: user/" + user_id +"/credit");

        return userLocalRepository.get(user_id).getCredits();
    }

    @PostMapping("/user/{user_id}/credit/add/{amount}")
    public Boolean addCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) throws JsonProcessingException {
        User user = userLocalRepository.get(user_id);
        user.setCredits(user.getCredits() + amount);
        userLocalRepository.update(user_id, user);

        boolean isSent = sender.send(user);
        LOGGER.info("Order sent: {}", mapper.writeValueAsString(Collections.singletonMap("isSent", isSent)));

        return true;
    }

    @PostMapping("/user/{user_id}/credit/subtract/{amount}")
    public Boolean subtractCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) {
        User user = userLocalRepository.get(user_id);

        if (user.getCredits() > amount) {
            return false;
        }

        user.setCredits(user.getCredits() - amount);
        userLocalRepository.update(user_id, user);

        return true;
    }


}
