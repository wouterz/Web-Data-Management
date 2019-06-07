package service.user.storage;

public interface Dao<T> {

    T create(T t);

    T get(String id);

    T update(long id, T t);

    boolean delete(long id);
}