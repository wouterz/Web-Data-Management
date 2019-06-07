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

    /**
     * Create a connection to the AWS Postgres database
     *
     * @return Connection to the database
     */
    private static Connection connectoRDS() {
        Connection c = null;
        try {
            c = DriverManager
                    .getConnection("jdbc:postgresql://webdata.cieofnaztu9b.us-east-1.rds.amazonaws.com:5432/webdata",
                            "webdata", "reverse123");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to database successfully");

        return c;
    }

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
