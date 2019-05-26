package service.order.storage;

import org.springframework.stereotype.Repository;
import service.order.models.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalRepository implements Dao {

    private List<User> Users = new ArrayList<>();

    @Override
    public long create(long id) {
        User user = new User(id, 0);
        Users.add(user);
        return id;
    }

    @Override
    public Object update(long id, Object user) {
        Users.set((int)id, (User) user);
        return user;
    }

    @Override
    public User get(long id) {
        List<Long> ids = new ArrayList<>(1);
        ids.add(id);
        return Users.stream().filter(p -> ids.contains(p.getId())).findFirst().orElse(null);
    }

    @Override
    public boolean delete(long id) {
        User user = Users.remove((int) id);
        return user.getId() != 0;
    }


}
