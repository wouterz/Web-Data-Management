package service.stock.RMI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("user-service")
public interface UserClient {

    @PostMapping("/user/create")
    long create();

    @DeleteMapping("/user/{user_id}")
    boolean remove(@PathVariable(value = "user_id") long user_id);

    @GetMapping("/user/{user_id}")
    String find(@PathVariable(value = "user_id") long user_id);

    @GetMapping("/user/{user_id}/credit")
    public long getCredits(@PathVariable(value = "user_id") long user_id);

    @PostMapping("/user/{user_id}/credit/add/{amount}")
    public Boolean addCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount) throws JsonProcessingException;

    @PostMapping("/user/{user_id}/credit/subtract/{amount}")
    public Boolean subtractCredits(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "amount") long amount);

}
