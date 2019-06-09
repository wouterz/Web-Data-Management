package service.payment.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import service.payment.models.Order;

@FeignClient("order-service")
public interface OrderClient {

    @RequestMapping(value="/order/find/{orderId}", method=GET)
    // GET - retrieve info about orderId: pay status, items, userId
    Order orderFind(@PathVariable String orderId);
}
