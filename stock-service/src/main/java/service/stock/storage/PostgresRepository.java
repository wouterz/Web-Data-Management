package service.stock.storage;

import org.springframework.stereotype.Repository;
import service.stock.models.StockItem;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;

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
                    .getConnection("jdbc:postgresql://webdata.cbcu76qz5fg7.us-east-1.rds.amazonaws.com:5432/webdata",
                            "webdata", "reverse123");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    /**
     * Creates new StockItem entry in the database
     *
     * @param o StockItem to add to the database
     * @return The StockItem if successful, null otherwise.
     */
    @Override
    public StockItem create(Object o) {
        connectoRDS();

        Statement statement;
        StockItem stock = (StockItem) o;
        try {
            statement = c.createStatement();
            String sql = "INSERT INTO STOCKITEM (ID,NAME,STOCK) "
                    + "VALUES (" + stockItemSQLFormat(stock) + ");";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("StockItem " + stock.toString() + " created successfully");
        return stock;
    }

    /**
     * Very basic way of retrieving a stock corresponding to id
     *
     * @param id Id of the StockItem
     * @return StockItem if it was found, null otherwise.
     */
    @Override
    public Object get(String id) {
        connectoRDS();

        Statement statement;
        StockItem foundStock = null;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM STOCKITEM;");
            while (rs.next()) {
                String currentId = rs.getString("id");
                String name = rs.getString("name");
                int stock = rs.getInt("stock");

                if (currentId.equals(id)) {
                    foundStock = new StockItem(currentId, name, stock);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        if (foundStock != null) {
            System.out.println("Stock found successfully");
            return foundStock;
        } else {
            System.out.println("Stock not found");
            return null;
        }
    }

    @SuppressWarnings("Duplicates")
    public StockItem alternativeGet(String id) {
        connectoRDS();


        Statement statement;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM STOCKITEM WHERE ID ='" + id + "';");
            rs.next();
            String currentId = rs.getString("id");
            String name = rs.getString("name");
            int stock = rs.getInt("stock");
            statement.close();

            return new StockItem(currentId, name, stock);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates the table entry corresponding to stock
     **/

    @Override
    public StockItem update(Object o) {
        connectoRDS();


        StockItem stockItem = (StockItem) o;
        Statement statement;
        try {
            statement = c.createStatement();
            String sql = "UPDATE STOCKITEM set STOCK = " + Integer.toString(stockItem.getStock()) + " where ID='" + stockItem.getId() + "' and NAME='" + stockItem.getName() + "';";
            statement.executeUpdate(sql);

            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Update done successfully");
        return stockItem;
    }

    /**
     * Deletes the stock corresponding to id from the database table
     *
     * @param o StockItem to be deleted
     * @return True if the deletion was done successful, false otherwise
     */
    @Override
    public boolean delete(Object o) {
        connectoRDS();


        StockItem stock = (StockItem) o;
        Statement statement;
        try {
            statement = c.createStatement();
            String sql = "DELETE from STOCKITEM where ID = '" + stock.getId() + "';";
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
     * Translates a StockItem object into a simple string to inject in an SQL statement
     *
     * @param stock The stock object to translate
     * @return SQL-valid string
     */
    private static String stockItemSQLFormat(StockItem stock) {
        String idString = stock.getId();
        String name = stock.getName();
        String stockString = Integer.toString(stock.getStock());

        return "'" + idString + "'" + ", '" + name + "', " + stockString;
    }
}
