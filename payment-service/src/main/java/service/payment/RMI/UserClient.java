package service.payment.RMI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import service.payment.models.StockItem;

@FeignClient("user-service")
public interface UserClient {

    @RequestMapping(value = "/user/{user_id}/credit/add/{amount}", method = RequestMethod.POST)
    Boolean addCredits(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "amount") long amount);

    @RequestMapping(value = "/user/{user_id}/credit/subtract/{amount}", method = RequestMethod.POST)
    Boolean subtractCredits(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "amount") long amount);

}
