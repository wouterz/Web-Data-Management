package user;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("user/create/")
    public long create() {
        return User.create(counter.incrementAndGet());
    }

    @DeleteMapping("/user/remove/")
    public boolean remove(@RequestParam(value = "user_id") long user_id) {
        return User.remove(user_id);
    }

    @GetMapping("/user/find/")
    public String find(@RequestParam(value = "user_id") long user_id) {
        return User.find(user_id).toString();
    }

    @GetMapping("/user/credit/")
    public long getCredits(@RequestParam(value = "user_id") long user_id) {
        return User.find(user_id).getCredits();
    }

    @PostMapping("/user/credit/subtract/")
    public Boolean addCredits(@RequestParam(value = "user_id") long user_id, @RequestParam(value = "amount") long amount) {
        User user = User.find(user_id);
        user.setCredits(user.getCredits() + amount);
        User.update(user);

        return true;
    }

    @PostMapping("/user/credit/add/")
    public Boolean subtractCredits(@RequestParam(value = "user_id") long user_id, @RequestParam(value = "amount") long amount) {
        User user = User.find(user_id);

        if (user.getCredits() > amount) {
            return false;
        }

        user.setCredits(user.getCredits() - amount);
        User.update(user);

        return true;
    }


}
