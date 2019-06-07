package service.order.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

    // Identifier for this order
    private final String orderId;
    // User that owns this order
    private final String userId;
    // list of item ids
    private List<String> items;

    // need this?
    private boolean isPayed;


    public Order(String userId, long orderId) {
        this.userId = userId;
        this.orderId = UUID.randomUUID().toString();

        this.items = new ArrayList<>();
        this.isPayed = false;
    }

    public String getorderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }
    public List<String> getItems() {
        return items;
    }
    public boolean getPaymentStatus(){
        return isPayed;
    }

    public Order addItem(String itemId) {
        this.items.add(itemId);

        return this;
    }

    public Order removeItem(long itemId) {
        this.items.remove(itemId);

        return this;
    }

}

