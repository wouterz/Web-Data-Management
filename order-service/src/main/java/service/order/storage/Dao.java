package service.order.storage;

public interface Dao<T> {

    long create(long id);

    T get(long id);

    boolean update(long id, T t);

    boolean delete(long id);
}