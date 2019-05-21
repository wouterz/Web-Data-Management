package redis.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

import redis.beans.User;
import redis.dao.UserDAO;

@RestController
public class UserController {

    private final AtomicLong counter = new AtomicLong(); 
    private UserDAO userDAO;
    
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("user/create/")
    public long create() {
        return userDAO.create(counter.incrementAndGet());
    }
    
    @DeleteMapping("/user/remove/")
    public boolean remove(@RequestParam(value = "userId") long userId) {
        return userDAO.remove(userId);
    }
    
    @GetMapping("/user/find/")
    public String find(@RequestParam(value = "userId") long userId) {
        return userDAO.find(userId);
    }
    
    @GetMapping("/user/credit/")
    public long getCredits(@RequestParam(value = "userId") long userId) {
        return userDAO.getCredits(userId);
    }
    
    @PostMapping("/user/credit/subtract/")
    public Boolean substractCredits(@RequestParam(value = "userId") long userId, @RequestParam(value = "amount") long amount) {
        return userDAO.setCredits(userId, userDAO.getCredits(userId) - amount);
    }
    
    @PostMapping("/user/credit/subtract/")
    public Boolean addCredits(@RequestParam(value = "userId") long userId, @RequestParam(value = "amount") long amount) {
        return userDAO.setCredits(userId, userDAO.getCredits(userId) + amount);
    }
}
