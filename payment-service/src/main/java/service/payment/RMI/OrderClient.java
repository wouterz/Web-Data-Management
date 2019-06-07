package service.payment.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("order-service")
public interface OrderClient {

    @PostMapping("/checkout/{orderId}")
    public boolean orderCheckout(@PathVariable(value = "orderId") long orderId);


    @PostMapping("/cancelcheckout/{orderId}")
    public boolean cancelOrderCheckout(@PathVariable(value = "orderId") long orderId);

    @GetMapping("/price/{orderId}")
    public int getPrice(@PathVariable(value = "orderId") long orderId);
}
