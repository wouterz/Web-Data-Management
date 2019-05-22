package service.user;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.user.storage.UserLocalRepository;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private final AtomicLong counter = new AtomicLong();
    private ObjectMapper mapper = new ObjectMapper();


    @Autowired
    UserSender sender;

    @Autowired
    UserLocalRepository userLocalRepository;



    @PostMapping("service/user/create/")
    public long create() {
        return userLocalRepository.create(counter.incrementAndGet());
    }

    @DeleteMapping("/service/user/remove/{user_id}")
    public boolean remove(@PathVariable(value = "user_id") long user_id) {
        return userLocalRepository.delete(user_id);
    }

    @GetMapping("/service/user/{user_id}")
    public String find(@PathVariable(value = "user_id") long user_id) {
        return userLocalRepository.find(user_id).toString();
    }

    @GetMapping("/service/user/{user_id}/credit/")
    public long getCredits(@PathVariable(value = "user_id") long user_id) {
        return userLocalRepository.get(user_id).getCredits();
    }

    @PostMapping("/service/user/{user_id}/credit/add/{amount}")
    public Boolean addCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) throws JsonProcessingException {
        User user = userLocalRepository.get(user_id);
        user.setCredits(user.getCredits() + amount);
        userLocalRepository.update(user_id, user);

        boolean isSent = sender.send(user);
        LOGGER.info("Order sent: {}", mapper.writeValueAsString(Collections.singletonMap("isSent", isSent)));

        return true;
    }

    @PostMapping("/service/user/{user_id}/credit/subtract/{amount}")
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
