package service.stock.storage;

import org.springframework.stereotype.Repository;

import service.stock.models.Order;
import service.stock.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LocalRepository implements Dao {
	
	private final AtomicLong counter = new AtomicLong();

    private List<Order> Orders = new ArrayList<>();

    @Override
    public String create(long userId) {
    	long orderId = counter.getAndIncrement();
        Order order = new Order(userId, orderId);
        Orders.add(order);
        return "no";
    }

    @Override
    public Object update(long id, Object order) {
        Orders.set((int)id, (Order) order);
        return order;
    }

    @Override
    public Order get(long id) {
        List<Long> ids = new ArrayList<>(1);
        ids.add(id);
        return Orders.stream().filter(p -> ids.contains(p.getorderId())).findFirst().orElse(null);
    }

    @Override
    public boolean delete(long id) {
    	Order order = Orders.remove((int) id);
        return order.getorderId() != 0;
    }


}
