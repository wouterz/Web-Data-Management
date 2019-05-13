package user;

import storage.Dao;

public class User {

    private static UserDao userDao = new UserDao();

    private final long id;
    private long credits;

    public User(long id, long credits) {
        this.id = id;
        this.credits = credits;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

    public static long create(long user_id) {
        return userDao.create(user_id);
    }

    static public boolean remove(long user_id) {
        return userDao.delete(user_id);
    }

    static public User find(long user_id) {
        return userDao.get(user_id);
    }

    static public User update(User user) {
        return userDao.update(user.id, user);
    }

}
