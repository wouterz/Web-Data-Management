package service.stock.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.stock.models.StockItem;

@FeignClient("order-service")
public interface OrderClient {

}
