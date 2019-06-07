package service.user.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import static java.lang.Math.toIntExact;
import service.user.models.User;

import org.springframework.stereotype.Repository;

@Repository
public class PostgresRepository implements Dao {


    @Override
    public String create(long id) {
        return "0";
    }

    @Override
    public Object get(long id) {
        return null;
    }

    @Override
    public Object update(long id, Object o) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
