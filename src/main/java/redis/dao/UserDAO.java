package redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import redis.beans.User;

@Repository
@Transactional
public class UserDAO {
	// private static final String KEY = "users";

	@Autowired
	private RedisTemplate<Long, Long> redisTemplate;

	public long create(long userID) {
		redisTemplate.opsForList().rightPush(userID, (long) 0);
		return userID;
	}

	public boolean remove(long userID) {
		redisTemplate.opsForList().rightPop(userID);
		return true;
	}

	public String find(long userID) {
		long size = redisTemplate.opsForList().size(userID);
		if (size > 0) {
			Long credit = redisTemplate.opsForList().index(userID, 0);
			User user = new User(userID, credit);
			return user.toString();
		}
		else
			return "User not found";
	}

	public long getCredits(long userID) {
		long size = redisTemplate.opsForList().size(userID);
		if (size > 0) {
			Long credit = redisTemplate.opsForList().index(userID, 0);
			return credit;
		}
		else
			return 0;
	}

	public Boolean setCredits(long userID, long amount) {
		long size = redisTemplate.opsForList().size(userID);
		if (size > 0) {
			redisTemplate.opsForList().set(userID, 0, amount);
			return true;
		}
		else
			return false;
	}
}
