package service.order.storage;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import service.order.models.Order;

@Repository
public class RedisRepository implements Dao {
    private static final String KEY = "orders";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Order> hashOps;

    @Override
    public String create(Object o) {
        Order order = (Order) o;
        hashOps.putIfAbsent(KEY, order.getOrderId(), order);
        return order.getOrderId();
    }

    @Override
    public Order get(String id) {
        Order order = hashOps.get(KEY, id);
        return order;
    }

    @Override
    public Order update(Object o) {
        Order order = (Order) o;
        hashOps.put(KEY, (order).getOrderId(), order);
        return order;
    }

    @Override
    public boolean delete(String orderId) {
        hashOps.delete(KEY, orderId);
        return hashOps.get(KEY, orderId) == null;
    }

}
