package service.order.storage;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import service.order.models.Order;

@Repository
public class RedisRepository implements Dao {

	private final AtomicLong counter = new AtomicLong();

	private static final String KEY = "orders";

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Order> hashOps;

	@Override
	public String create(String userId) {
		long orderId = counter.getAndIncrement();
		Order order = new Order(userId, orderId);
		hashOps.putIfAbsent(KEY, order.getorderId(), order);
		return order.getorderId();
	}

	@Override
	public Object get(long id) {
		Order order = hashOps.get(KEY, id);
		return order;
	}

	@Override
	public Object update(long id, Object order) {
		hashOps.put(KEY, ((Order) order).getorderId(), (Order) order);
		return order;
	}

	@Override
	public boolean delete(long id) {
		hashOps.delete(KEY, id);
		return hashOps.get(KEY, id) == null;
	}

}
