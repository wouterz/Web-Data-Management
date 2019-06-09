package service.order.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import service.order.models.StockItem;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient("payment-service")
public interface PaymentClient {
    @RequestMapping(value="/payment/pay/{user_id}/{order_id}", method=POST)
    public String create(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "order_id") String order_id);

    @RequestMapping(value="/payment/cancel/{user_id}/{order_id}", method=POST)
    public String cancel(@PathVariable(value = "user_id") String user_id, @PathVariable(value = "order_id") String order_id);

    @RequestMapping(value="/payment/status/{order_id}", method=GET)
    public String status(@PathVariable(value = "order_id") String order_id);
}
