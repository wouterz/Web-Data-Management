package service.stock.models;

import java.io.Serializable;

public class StockItem implements Serializable {
	private static final long serialVersionUID = 1L;

    private final long id;
    private final String name;
    private int stock;

    public StockItem(long id, String name){
        this.id = id;
        this.name = name;
        this.stock = 0;
    }

    public void addToStock(int amount){
        this.stock += amount;
    }

    public long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getStock(){
        return this.stock;
    }

}
