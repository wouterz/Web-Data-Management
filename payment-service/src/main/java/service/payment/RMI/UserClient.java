package service.payment.RMI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import service.payment.models.StockItem;

@FeignClient("user-service")
public interface UserClient {

    @RequestMapping("/user/{user_id}/credit/add/{amount}")
    Boolean addCredits(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "amount") long amount);

    @RequestMapping("/user/{user_id}/credit/subtract/{amount}")
    Boolean subtractCredits(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "amount") long amount);

}
