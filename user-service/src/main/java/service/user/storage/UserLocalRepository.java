package service.user.storage;

import org.springframework.stereotype.Repository;
import service.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserLocalRepository implements Dao {

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
//        Optional<User> user = Users.stream().filter(p ->  p.getId().equals(id)).findFirst();

//        return user.orElse(null);
        return null;
    }

    @Override
    public boolean delete(long id) {
        User user = Users.remove((int) id);
        return user.getId() != 0;
    }

    public List<User> find(Long id) {
        List<Long> ids = new ArrayList<>(1);
        ids.add(id);
        return Users.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toList());
    }
}
