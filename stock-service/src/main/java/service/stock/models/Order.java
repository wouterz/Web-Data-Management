package service.stock.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// import org.springframework.stereotype.Repository;
// import org.springframework.data.redis.core.RedisHash;


// @RedisHash("Order")
public class Order implements Serializable{

    // ??
    private static final long serialVersionUID = 1L;

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
    // TODO: implement
    public boolean addItem() {
        return true;
    }

}

