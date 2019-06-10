package service.payment.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;
import service.payment.models.Payment;

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
    public Payment create(Object o) {
        Statement statement;
        Payment payment = (Payment) o;
        try {
            statement = c.createStatement();
            String sql = "INSERT INTO PAYMENT (ID,PAYMENTSTATUS,USERID,ORDERID,CREDITS) "
                    + "VALUES (" + paymentSQLFormat(payment) + ");";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Payment " + payment.toString() + " created successfully");
        return payment;
    }

    public Payment alternativeGet(String paymentId) {
        Statement statement;
        Payment foundPayment = null;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PAYMENT;");
            while (rs.next()) {
                String currentPaymentId = rs.getString("id");
                Payment.PaymentStatus paymentStatus = Payment.PaymentStatus.valueOf(rs.getString("paymentstatus"));
                String currentUserId = rs.getString("userid");
                String currentOrderId = rs.getString("orderid");
                long currentCredits = rs.getLong("credits");

                if (currentPaymentId.equals(paymentId)) {
                    foundPayment = new Payment(currentPaymentId, paymentStatus, currentUserId, currentOrderId, currentCredits);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        if (foundPayment != null) {
            System.out.println("Stock found successfully");
            return foundPayment;
        } else {
            System.out.println("Stock not found");
            return null;
        }
    }

    @Override
    public Payment get(String id) {
        Statement statement;

        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PAYMENT WHERE ID ='" + id + "';");
            rs.next();
            String currentPaymentId = rs.getString("id");
            Payment.PaymentStatus paymentStatus = Payment.PaymentStatus.valueOf(rs.getString("paymentstatus"));
            String currentUserId = rs.getString("userid");
            String currentOrderId = rs.getString("orderid");
            long currentCredits = rs.getLong("credits");
            statement.close();

            return new Payment(currentPaymentId, paymentStatus, currentUserId, currentOrderId, currentCredits);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public Payment update(Object o) {
        Payment payment = (Payment) o;
        Statement statement;

        try {
            statement = c.createStatement();
            String sql = "UPDATE PAYMENT set PAYMENTSTATUS = '" + payment.getPaymentStatus().name() + "', CREDITS = " + Long.toString(payment.getCredits()) + " where ID='" + payment.getId() + "' and USERID='" + payment.getUserId() + "' and ORDERID='" + payment.getOrderId() + "';";
            statement.executeUpdate(sql);

            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
        System.out.println("Update done successfully");
        return payment;
    }

    @Override
    public boolean delete(String paymentId) {
        Statement statement;
        try {
            statement = c.createStatement();
            String sql = "DELETE from PAYMENT where ID = '" + paymentId + "';";
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
     * @return SQL-valid string
     */
    private static String paymentSQLFormat(Payment payment) {
        String paymentId = payment.getId();
        String paymentStatus = payment.getPaymentStatus().name();
        String userId = payment.getUserId();
        String orderId = payment.getOrderId();
        long credits = payment.getCredits();

        return "'" + paymentId + "', '" + paymentStatus + "', '" + userId + "', '" + orderId + "', " + Long.toString(credits);
    }
}
