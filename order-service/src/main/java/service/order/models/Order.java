package service.order.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable{

    // Identifier for this order
    private final long orderId;
    // User that owns this order
    private final long userId;
    // list of item ids
    private List<Long> items;

    // need this?
    private boolean isPayed;


    public Order(long userId, long orderId) {
        this.userId = userId;
        this.orderId = orderId;


        this.items = new ArrayList<>();
        this.isPayed = false;
    }

    public long getorderId() {
        return orderId;
    }

    public long getUserId() {
        return userId;
    }
    public List<Long> getItems() {
        return items;
    }
    public boolean getPaymentStatus(){
        return isPayed;
    }

    public Order addItem(long itemId) {
        this.items.add(itemId);

        return this;
    }

    public Order removeItem(long itemId) {
        this.items.remove(itemId);

        return this;
    }

}

