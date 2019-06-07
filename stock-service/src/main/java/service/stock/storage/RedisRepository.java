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
    public StockItem create(Object o) {
        StockItem stock = (StockItem) o;
        hashOps.putIfAbsent(KEY, stock.getId(), stock);
        return stock;
    }

    @Override
    public StockItem get(String id) {
        StockItem stockItem = hashOps.get(KEY, id);
        return stockItem;
    }

    @Override
    public StockItem update(Object o) {
        StockItem stockItem = (StockItem) o;
        hashOps.put(KEY, ((StockItem) stockItem).getId(), (StockItem) stockItem);
        return stockItem;
    }

    @Override
    public boolean delete(Object o) {
        StockItem stock = (StockItem) o;
        hashOps.delete(KEY, stock.getId());
        return hashOps.get(KEY, stock.getId()) == null;
    }
}
