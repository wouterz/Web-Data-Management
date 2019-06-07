package service.order.storage;

public interface Dao<T> {

    String create(String id);

    T get(long id);

    T update(long id, T t);

    boolean delete(long id);
}