package service.order.storage;

public interface Dao<T> {

    T create(T t);

    T get(long id);

    T update(long id, T t);

    boolean delete(long id);
}