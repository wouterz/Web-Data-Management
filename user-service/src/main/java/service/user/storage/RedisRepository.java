package service.user.storage;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import service.user.models.User;

@Repository
public class RedisRepository implements Dao {

    private static final String KEY = "users";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, User> hashOps;

    @Override
    public User create(Object o) {
        User user = (User) o;
        hashOps.putIfAbsent(KEY, user.getId(), user);
        return user;
    }

    @Override
    public User get(String id) {
        User user = hashOps.get(KEY, id);
        return user;
    }

    @Override
    public String update(String id, long credit) {
        User user = new User(id, credit);
        hashOps.put(KEY, ((User) user).getId(), (User) user);
        return id;
    }

    @Override
    public boolean delete(Object o) {
        User user = (User) o;
        String id = user.getId();
        hashOps.delete(KEY, id);
        return hashOps.get(KEY, id) == null;
    }
}
