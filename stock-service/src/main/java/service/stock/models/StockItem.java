package service.stock.models;

import java.io.Serializable;
import java.util.UUID;

public class StockItem implements Serializable {
	private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private int stock;

    public StockItem(long id, String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.stock = 0;
    }

    public void addToStock(int amount){
        this.stock += amount;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getStock(){
        return this.stock;
    }

}
