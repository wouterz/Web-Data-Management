package service.user.storage;

public interface Dao<T> {

    T create(T t);

    T get(String id);

    T update(T t);

    boolean delete(T t);
}