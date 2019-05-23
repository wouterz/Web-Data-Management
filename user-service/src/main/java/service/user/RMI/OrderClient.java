package service.user.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.user.models.StockItem;

@FeignClient("stock-service")
public interface OrderClient {

    @RequestMapping(method = RequestMethod.POST, value = "/stock/item/create/")
    StockItem createStockItem(@RequestParam("name") String name);

}
