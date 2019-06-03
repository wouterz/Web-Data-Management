package service.user.storage;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import service.user.models.User;

@Repository
public class RedisRepository implements Dao {

	private static final String KEY = "users";

	@Resource(name = "redisTemplate")
	private HashOperations<String, Long, User> hashOps;

	@Override
	public long create(long id) {
		User user = new User(id, 0);
		hashOps.putIfAbsent(KEY, id, user);
		return id;
	}

	@Override
	public Object get(long id) {
		User user = hashOps.get(KEY, id);
		return user;
	}

	@Override
	public Object update(long id, Object user) {
		hashOps.put(KEY, id, (User) user);
		return user;
	}

	@Override
	public boolean delete(long id) {
		hashOps.delete(KEY, id);
        return hashOps.get(KEY, id) == null;
	}
}
