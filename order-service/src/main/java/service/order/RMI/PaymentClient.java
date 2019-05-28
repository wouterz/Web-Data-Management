package service.order.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import service.order.models.StockItem;

@FeignClient("payment-service")
public interface PaymentClient {
    @PostMapping("/payment/pay/{user_id}/{order_id}")
    public long create(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "order_id") long order_id);

    @PostMapping("/payment/cancel/{user_id}/{order_id}")
    public long cancel(@PathVariable(value = "user_id") long user_id, @PathVariable(value = "order_id") long order_id);

    @GetMapping("/payment/status/{order_id}")
    public long status(@PathVariable(value = "order_id") long order_id);
}
