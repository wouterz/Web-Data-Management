package service.order.storage;

import org.springframework.stereotype.Repository;
import service.order.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgresRepository implements Dao {

    private static Connection c = connectoRDS();

    /**
     * Connect to the AWS postgres instance
     * @return true if successful, false otherwise
     */
    private static Connection connectoRDS() {
        try {
            c = DriverManager
                    .getConnection("jdbc:postgresql://webdata.cbcu76qz5fg7.us-east-1.rds.amazonaws.com:5432/webdata",
                            "webdata", "reverse123");
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    @Override
    public Order create(Object o) {
        Statement statement;
        Order order = new Order(o.toString());
        try {
            statement = c.createStatement();
            String sql = "INSERT INTO ORDERS (ID,USERID,ITEMS,ISPAYED) "
                    + "VALUES (" + orderSQLFormat(order) + ");";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Order " + order.toString() + " created successfully");
        return order;
    }

    @Override
    public Order get(String id) {
        Statement statement;
        Order foundOrder = null;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ORDERS;");
            while (rs.next()) {
                String currentOrderId = rs.getString("id");
                String currentUserId = rs.getString("userid");
                ArrayList<String> currentItems = sqlArrayToArrayList(rs.getArray("items"));
                boolean currentIsPayed = rs.getBoolean("ispayed");

                if (currentOrderId.equals(id)) {
                    foundOrder = new Order(currentOrderId, currentUserId, currentItems, currentIsPayed);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        if (foundOrder != null) {
            System.out.println("Stock found successfully");
            return foundOrder;
        } else {
            System.out.println("Stock not found");
            return null;
        }
    }

    @SuppressWarnings("Duplicates")
    public Order alternativeGet(String id) {
        Statement statement;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ORDERS WHERE ID ='" + id + "';");
            rs.next();
            String currentOrderId = rs.getString("id");
            String currentUserId = rs.getString("userid");
            ArrayList<String> currentItems = sqlArrayToArrayList(rs.getArray("items"));
            boolean currentIsPayed = rs.getBoolean("ispayed");
            statement.close();

            return new Order(currentOrderId, currentUserId, currentItems, currentIsPayed);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public Order update(Object o) {
        Order order = (Order) o;
        Statement statement;

        try {
            statement = c.createStatement();
            String sql = "UPDATE ORDERS set ITEMS = " + itemListToString(order.getItems()) + ", ISPAYED = " + Boolean.toString(order.getPaymentStatus()) + " where ID='" + order.getOrderId() + "' and USERID='" + order.getUserId() + "';";
            statement.executeUpdate(sql);

            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Update done successfully");
        return order;
    }

    @Override
    public boolean delete(String orderId) {
        Statement statement;
        try {
            statement = c.createStatement();
            String sql = "DELETE from ORDERS where ID = '" + orderId + "';";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        System.out.println("Deletion done successfully");
        return true;
    }

    /**
     * Translates a order object into a simple string to inject in an SQL statement
     *
     * @param order The user object to translate
     * @return SQL-valid string
     */
    private static String orderSQLFormat(Order order) {
        String orderId = order.getOrderId();
        String userId = order.getUserId();


        return "'" + orderId + "', '" + userId + "', " + itemListToString(order.getItems()) + ", " + Boolean.toString(order.getPaymentStatus());
    }

    private static String itemListToString(List<String> items) {
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


    private static ArrayList<String> sqlArrayToArrayList(Array items) throws SQLException {
        ArrayList<String> result = new ArrayList<>();

        String[] currentItemsList = (String[]) items.getArray();
        for (String s : currentItemsList) {
            result.add(s);
        }

        return result;
    }

}
