package service.order.RMI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.order.models.StockItem;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient("stock-service")
public interface StockClient {

    // Add to the stock of an item by supplying the id
    @RequestMapping(value= "/stock/add/{id}/{amount}", method=POST)
    public StockItem addStock(@PathVariable(value="id") String id, @PathVariable(value="amount") int amount);

    // Subtract from the stock of an item by supplying the id
    @RequestMapping(value= "/stock/subtract/{id}/{amount}", method=POST)
    public StockItem subtractStock(@PathVariable(value="id") String id, @PathVariable(value="amount") int amount);

    @RequestMapping(value= "/stock/find/{id}", method=GET)
    public StockItem findStockItem(@PathVariable(value="id") String id);

}
