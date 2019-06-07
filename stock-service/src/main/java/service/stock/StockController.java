package service.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.stock.models.StockItem;
import service.stock.storage.Dao;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);


    // Temporary way to generate ID
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private Dao<StockItem> localRepository;

    // Create a new stock item by supplying the name
    @RequestMapping(value= "/stock/item/create", method=POST)
    public long createStockItem() {
        LOGGER.info("REQUEST: /stock/item/create");

        return localRepository.create(counter.getAndIncrement());
    }

    // Get info for a stock by supplying the id
    @RequestMapping(value= "/stock/availability/{id}", method=GET)
    public int getStockItem(@PathVariable(value="id") long id) {
        // Should get Stock item from database

        return localRepository.get(id).getStock();
    }

    // Add to the stock of an item by supplying the id
    @RequestMapping(value= "/stock/add/{id}/{amount}", method=POST)
    public StockItem addStock(@PathVariable(value="id") long id,
                              @PathVariable(value="amount") int amount) {
        return updateStock(id, amount);
    }

    // Subtract from the stock of an item by supplying the id
    @RequestMapping(value= "/stock/subtract/{id}/{amount}", method=POST)
    public StockItem subtractStock(@PathVariable(value="id") long id,
                              @PathVariable(value="amount") int amount) {
        return updateStock(id, -amount);
    }

    // Update stock method used by the add and subtract endpoint
    private StockItem updateStock(long id, int amount) {
        // First it should get the stock item from the database here
        // We dont have a database connection yet, so we will make a placeholder stock item
        StockItem item = localRepository.get(id);

        // Check if valid amount
        if ((item.getStock() + amount) < 0) {
            return null;
        }

        // Change stock
        item.addToStock(amount);

        // Save the changes to the database
        return localRepository.update(id, item);
    }
}
