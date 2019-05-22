package service.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);


    // Temporary way to generate ID
    private final AtomicLong counter = new AtomicLong();

    // Create a new stock item by supplying the name
    @RequestMapping(value= "/stock/item/create", method=POST)
    public StockItem createStockItem(@RequestParam(value="name") String name) {
        LOGGER.info("REQUEST: /stock/item/create");
        // Stock item should be added to database
        return new StockItem(counter.getAndIncrement(), name);
    }

    // Get info for a stock by supplying the id
    @RequestMapping(value= "/stock/availability", method=GET)
    public StockItem getStockItem(@RequestParam(value="id") long id) {
        // Should get Stock item from database
        return new StockItem(id, "fromDatabase");
    }

    // Add to the stock of an item by supplying the id
    @RequestMapping(value= "/stock/add", method=POST)
    public StockItem addStock(@RequestParam(value="id") long id,
                              @RequestParam(value="amount") int amount) {
        return updateStock(id, amount);
    }

    // Subtract from the stock of an item by supplying the id
    @RequestMapping(value= "/stock/subtract", method=POST)
    public StockItem subtractStock(@RequestParam(value="id") long id,
                              @RequestParam(value="amount") int amount) {
        return updateStock(id, -amount);
    }

    // Update stock method used by the add and subtract endpoint
    private StockItem updateStock(long id, int amount) {
        // First it should get the stock item from the database here
        // We dont have a database connection yet, so we will make a placeholder stock item
        StockItem toAdd = new StockItem(id, "fromDatabase");

        // Change stock
        toAdd.addToStock(amount);

        // Save the changes to the database, again we can not do this now, and then return the updated object
        return toAdd;
    }
}
