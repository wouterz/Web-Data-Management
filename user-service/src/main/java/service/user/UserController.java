package service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.user.models.User;
import service.user.storage.Dao;
import service.user.storage.RedisRepository;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RedisRepository localRepository;

    @PostMapping("/user/create")
    public long create() {
        LOGGER.info("Request POST: /user/create");
        User user = new User(counter.getAndIncrement(), 0);

        return localRepository.create(counter.get());
    }

    @DeleteMapping("/user/{user_id}")
    public boolean remove(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request DELETE: /user/remove/" + user_id);

        return localRepository.delete(user_id);
    }

    @GetMapping("/user/{user_id}")
    public User find(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request GET: user/" + user_id);

        return (User)localRepository.get(user_id);
    }

    @GetMapping("/user/{user_id}/credit")
    public long getCredits(@PathVariable(value = "user_id") long user_id) {
        LOGGER.info("Request: user/" + user_id +"/credit");

        return ((User)(localRepository.get(user_id))).getCredits();
    }

    

    @PostMapping("/user/{user_id}/credit/add/{amount}")
    public boolean addCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) {
        User user = (User)localRepository.get(user_id);
        user.setCredits(user.getCredits() + amount);
        localRepository.update(user_id, user);

        return true;
    }

    @PostMapping("/user/{user_id}/credit/subtract/{amount}")
    public boolean subtractCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) {
        User user = (User)localRepository.get(user_id);

        if (user.getCredits() > amount) {
            return false;
        }

        user.setCredits(user.getCredits() - amount);
        localRepository.update(user_id, user);

        return true;
    }

    @GetMapping("/user")
    public String endpoint() {

        return "This is the user service";
    }


}
