package service.payment.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import service.payment.models.Order;

@FeignClient("order-service")
public interface OrderClient {

    @GetMapping("/find/{orderId}")
    // GET - retrieve info about orderId: pay status, items, userId
    Order orderFind(@PathVariable String orderId);
}
