package service.user;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.user.messaging.UserSender;
import service.user.models.User;
import service.user.storage.Dao;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private UserSender sender;

    @Autowired
    private Dao<User> localRepository;



    @PostMapping("/create")
    public long create() {
        LOGGER.info("Request POST: /user/create");
        User user = new User(counter.getAndIncrement(), 0);
        boolean send = sender.send(user);
        LOGGER.info("Request: Message : " + user.toString() +" was sent: " + send);


        return localRepository.create(counter.get());
    }

    @DeleteMapping("/{user_id}")
    public boolean remove(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request DELETE: /user/remove/" + user_id);

        return localRepository.delete(user_id);
    }

    @GetMapping("/{user_id}")
    public String find(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request GET: user/" + user_id);

        return localRepository.get(user_id).toString();
    }

    @GetMapping("/{user_id}/credit")
    public long getCredits(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request: user/" + user_id +"/credit");

        return localRepository.get(user_id).getCredits();
    }

    @PostMapping("/{user_id}/credit/add/{amount}")
    public Boolean addCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) throws JsonProcessingException {
        User user = localRepository.get(user_id);
        user.setCredits(user.getCredits() + amount);
        localRepository.update(user_id, user);

        boolean isSent = sender.send(user);
        LOGGER.info("Order sent: {}", mapper.writeValueAsString(Collections.singletonMap("isSent", isSent)));

        return true;
    }

    @PostMapping("/{user_id}/credit/subtract/{amount}")
    public Boolean subtractCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) {
        User user = localRepository.get(user_id);

        if (user.getCredits() > amount) {
            return false;
        }

        user.setCredits(user.getCredits() - amount);
        localRepository.update(user_id, user);

        return true;
    }


}
