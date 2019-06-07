package service.user.storage;

public interface Dao<T> {

    String create(long id);

    T get(long id);

    T update(long id, T t);

    boolean delete(long id);
}