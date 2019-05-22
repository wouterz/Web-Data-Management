package service.user.storage;

import org.springframework.stereotype.Repository;

@Repository
public class UserReddisRepository implements Dao {


    @Override
    public long create(long id) {
        return 0;
    }

    @Override
    public Object get(long id) {
        return null;
    }

    @Override
    public Object update(long id, Object o) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
