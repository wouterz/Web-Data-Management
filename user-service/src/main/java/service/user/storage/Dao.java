package service.user.storage;

public interface Dao<T> {

    T create(T t);

    T get(String id);

    String update(String id, long credit);

    boolean delete(T t);
}