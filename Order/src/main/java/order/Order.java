package main.java.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// import org.springframework.stereotype.Repository;
// import org.springframework.data.redis.core.RedisHash;



// @RedisHash("Order")
public class Order implements Serializable{

    // ??
    private static final long serialVersionUID = 1L;

	private final long orderId;
    private final String userId;
    private List<Long> items;

    // need this?
    private boolean isPayed;


    public Order(String userId, long orderId) {
        this.userId = userId;
        this.orderId = orderId;


        this.items = new ArrayList<>();
        this.isPayed = false;
    }

    public String getorderId() {
        return orderId+"";
    }

    public String getUserId() {
        return userId;
    }
    public List<Long> getItems() {
        return items;
    }
    public boolean getPaymentStatus(){
        return isPayed;
    }

}

