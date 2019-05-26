package service.stock.storage;

public interface Dao<T> {

    long create(long id);

    T get(long id);

    T update(long id, T t);

    boolean delete(long id);
}