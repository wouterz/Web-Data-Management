package service.stock.models;

import java.io.Serializable;
import java.util.UUID;

public class StockItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private int stock;

    /**
     * Constructor for existing StockItems retrieved from the database
     *
     * @param id   Id of the stockItem
     * @param name Name of the stockItem
     */
    public StockItem(String id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    /**
     * Constructor for a new StockItem
     * @param name Name of the StockItem
     */
    public StockItem(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.stock = 0;
    }

    public void addToStock(int amount) {
        this.stock += amount;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getStock() {
        return this.stock;
    }
}
