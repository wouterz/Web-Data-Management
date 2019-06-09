package service.payment.storage;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import service.payment.models.Order;
import service.payment.models.Payment;
import service.payment.models.Payment.PaymentStatus;

@Repository
public class RedisRepository implements Dao {

    private static final String KEY = "payments";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Payment> hashOps;


    @Override
    public Payment create(Object o) {
        Payment payment = (Payment) o;
        hashOps.putIfAbsent(KEY, payment.getId(), payment);
        return payment;
    }

    @Override
    public Object get(String id) {
        Payment payment = hashOps.get(KEY, id);
        return payment;
    }

    @Override
    public Object update(Object o) {
        Payment payment = (Payment) o;
        hashOps.put(KEY, payment.getId(), payment);
        return payment;
    }

    @Override
    public boolean delete(String id) {
        hashOps.delete(KEY, id);
        return hashOps.get(KEY, id) == null;
    }
}