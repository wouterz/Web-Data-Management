package service.user.storage;

import org.springframework.stereotype.Repository;
import service.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserReddisRepository {

    private List<User> Users = new ArrayList<>();

    public User add(User user) {
//        user.setId((long) (Users.size()+1));
        user = new User(123, 0);
        Users.add(user);
        return user;
    }

    public User update(User user) {
        Users.set(123, user);
        return user;
    }

    public User findById(Long id) {
//        Optional<User> user = Users.stream().filter(p ->  p.getId().equals(id)).findFirst();

//        return user.orElse(null);
        return null;
    }

    public void delete(Long id) {
        Users.remove(id.intValue());
    }

    public List<User> find(List<Long> ids) {
        return Users.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toList());
    }
}
