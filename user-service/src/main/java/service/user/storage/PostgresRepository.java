package service.user.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import service.user.models.User;

import org.springframework.stereotype.Repository;

@Repository
public class PostgresRepository implements Dao {

    private static Connection c;

    /**
     * Connect to the AWS postgres instance
     * @return true if successful, false otherwise
     */
    private static void connectoRDS() {
        if (c != null) return;
        try {
            c = DriverManager
                    .getConnection("jdbc:postgresql://webdata2.cbcu76qz5fg7.us-east-1.rds.amazonaws.com:5432/webdata",
                            "webdata2", "reverse123");
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Create a database entry for the user given
     * @param o User object to be written to the database
     * @return The user object if it is successfully written to the database, otherwise null
     */
    @Override
    public User create(Object o) {
        connectoRDS();
        
        Statement statement;
        User user = (User) o;
        try {
            statement = c.createStatement();
            String sql = "INSERT INTO USERS (ID,CREDITS) "
                    + "VALUES (" + userSQLFormat(user) + ");";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("User " + user.toString() + " created successfully");
        return user;
    }

    /**
     * Very basic way of retrieving a user corresponding to id
     *
     * @param id Id of the user to be retrieved
     * @return The user if found in the table, null otherwise
     */
    public User alternativeGet(String id) {
        connectoRDS();

        Statement statement;
        User foundUser = null;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS;");
            while (rs.next()) {
                String currentId = rs.getString("id");
                long credit = rs.getLong("credits");

                if (currentId.equals(id)) {
                    foundUser = new User(currentId, credit);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        if (foundUser != null) {
            System.out.println("User found successfully");
            return foundUser;
        } else {
            System.out.println("User not found");
            return null;
        }
    }

    @Override
    public User get(String id) {
        connectoRDS();

        Statement statement;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS WHERE ID ='" + id + "';");
            rs.next();
            String currentId = rs.getString("id");
            long credit = rs.getLong("credits");
            statement.close();

            return new User(currentId, credit);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates the table entry corresponding to user
     *
     * Object o User to be updated in the database
     *
     * @return The id of updated user if successful, null otherwise
     */
    @Override
    public User update(Object o) {
        connectoRDS();


        Statement statement;
        User user = (User) o;

        try {
            statement = c.createStatement();
            String sql = "UPDATE USERS set CREDITS = " + Long.toString(user.getCredits()) + " where ID='" + user.getId() + "';";
            statement.executeUpdate(sql);

            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Update done successfully");
        return user;
    }

    /**
     * Deletes the given user from the database
     * @param userId Id of the user to be deleted
     * @return True if deletion done or nothing done, false if something went wrong
     */
    @Override
    public boolean delete(String userId) {
        connectoRDS();

        Statement statement;

        try {
            statement = c.createStatement();
            String sql = "DELETE from USERS where ID ='" + userId + "';";
            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
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

        return "'" + idString + "', '" + creditString + "'";
    }
}
