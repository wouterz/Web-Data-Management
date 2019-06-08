package service.order.storage;

import org.springframework.stereotype.Repository;
import service.order.models.Order;
import service.order.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

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
                    .getConnection("jdbc:postgresql://webdata.cbcu76qz5fg7.us-east-1.rds.amazonaws.com:5432/webdata",
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
    public Order create(Object o) {
        Connection c = connectoRDS();

        Statement statement;
        Order order = (Order) o;
        try {
            statement = c.createStatement();
            String sql = "INSERT INTO ORDERS (ID,USERID,ITEMS,ISPAYED) "
                    + "VALUES (" + orderSQLFormat(order) + ");";
            statement.executeUpdate(sql);
            statement.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("User " + order.toString() + " created successfully");
        return order;
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
     * Translates a order object into a simple string to inject in an SQL statement
     *
     * @param order The user object to translate
     * @return SQL-valid string
     */
    private static String orderSQLFormat(Order order) {
        String orderId = order.getorderId();
        String userId = order.getUserId();


        return "'" + orderId + "', '" + userId + "', ";
    }

    private static String itemListToString(ArrayList<String> items) {
        String result = "'{";
        for (int j = 0; j < items.size(); j++) {
            if (j == items.size() - 1) {
                result = result + '"' + items.get(j) + '"';
            } else {
                result = result + '"' + items.get(j) + '"' + ',';
            }
        }
        return result + "}'";
    }

}
