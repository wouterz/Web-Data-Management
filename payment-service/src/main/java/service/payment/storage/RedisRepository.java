package service.payment.storage;

import org.springframework.stereotype.Repository;
import service.payment.models.Payment;

@Repository
public class RedisRepository implements Dao {

    @Override
    public Payment create(Object o) {
        return null;
    }

    @Override
    public Payment get(String id) {
        return null;
    }

    @Override
    public Payment update(Object o) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
