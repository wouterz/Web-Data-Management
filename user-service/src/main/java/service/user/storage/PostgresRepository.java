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

    /**
     * Create a database entry for the user given
     * @param o User object to be written to the database
     * @return The user object if it is successfully written to the database, otherwise null
     */
    @Override
    public User create(Object o) {
        Connection c = connectoRDS();
        Statement statement;
        User user = (User) o;
        try {
            statement = c.createStatement();
            String sql = "INSERT INTO USERS (ID,CREDIT) "
                    + "VALUES (" + userSQLFormat(user) + ");";
            statement.executeUpdate(sql);
            statement.close();
            c.close();
        } catch (Exception e) {
            System.out.println("User already exists, no user entry created");
            return null;
        }
        System.out.println("User " + user.toString() + " created successfully");
        return user;
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

    /**
     * Translates a user object into a simple string to inject in an SQL statement
     *
     * @param user The user object to translate
     * @return SQL-valid string
     */
    private static String userSQLFormat(User user) {
        String idString = user.getId();
        String creditString = Long.toString(user.getCredits());

        return idString + ", " + creditString;
    }
}
