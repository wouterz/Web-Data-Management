package service.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.stock.models.StockItem;
import service.stock.storage.RedisRepository;
import service.stock.storage.PostgresRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);


    @Autowired
    private PostgresRepository localRepository;

    // Create a new stock item by supplying the name
    @RequestMapping(value = "/stock/item/create", method = POST)
    public StockItem createStockItem(String name) {
        LOGGER.info("REQUEST: /stock/item/create");

        StockItem stock = new StockItem(name);

        return localRepository.create(stock);
    }

    // Get info for a stock by supplying the id
    @RequestMapping(value = "/stock/availability/{id}", method = GET)
    public int getStockItem(@PathVariable(value = "id") String id) {
        // Should get Stock item from database

        return ((StockItem) localRepository.get(id)).getStock();
    }

    // Add to the stock of an item by supplying the id
    @RequestMapping(value = "/stock/add/{id}/{amount}", method = POST)
    public StockItem addStock(@PathVariable(value = "id") String id,
                              @PathVariable(value = "amount") int amount) {
        return updateStock(id, amount);
    }

    // Subtract from the stock of an item by supplying the id
    @RequestMapping(value = "/stock/subtract/{id}/{amount}", method = POST)
    public StockItem subtractStock(@PathVariable(value = "id") String id,
                                   @PathVariable(value = "amount") int amount) {
        return updateStock(id, -amount);
    }

    // Update stock method used by the add and subtract endpoint
    private StockItem updateStock(String id, int amount) {
        StockItem item = (StockItem) localRepository.get(id);

        // Change stock
        item.addToStock(amount);

        // Save the changes to the database
        return localRepository.update(item);
    }

    @GetMapping("/stock")
    public String endpoint() {

        return "This is the stock service";
    }

    @RequestMapping(value = "/stock/find/{id}", method = GET)
    public StockItem findStockItem(@PathVariable(value = "id") String id) {
        // Should get Stock item from database

        return ((StockItem) localRepository.get(id));
    }
}
