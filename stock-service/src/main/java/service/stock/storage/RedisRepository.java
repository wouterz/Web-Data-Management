package service.stock.storage;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import service.stock.models.StockItem;


@Repository
public class RedisRepository implements Dao {

	private static final String KEY = "stockitems";

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, StockItem> hashOps;

	@Override
	public String create(long id) {
		StockItem stockItem = new StockItem(id, "emptyName");
		hashOps.putIfAbsent(KEY, stockItem.getId(), stockItem);
		return stockItem.getId();
	}

	@Override
	public Object get(long id) {
		StockItem stockItem = hashOps.get(KEY, id);
		return stockItem;
	}

	@Override
	public Object update(long id, Object stockItem) {
		hashOps.put(KEY, ((StockItem)stockItem).getId(), (StockItem) stockItem);
		return stockItem;
	}

	@Override
	public boolean delete(long id) {
		hashOps.delete(KEY, id);
        return hashOps.get(KEY, id) == null;
	}
}
