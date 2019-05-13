package user;

import storage.Dao;

public class UserDao implements Dao<User> {
    @Override
    public long create(long id) {
        return id;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public User update(long id, User user) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return true;
    }
}
