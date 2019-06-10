package service.payment.models;

import java.util.UUID;
import java.io.Serializable;
public class Payment implements Serializable{

    public enum PaymentStatus{
        PAID, UNPAID
    }

    private static final long serialVersionUID = 1L;


    private String id;
    private PaymentStatus paymentStatus;
    private String userId;
    private String orderId;
    private long credits;

    /**
     * Constructor for new payments
     */
    public Payment(PaymentStatus paymentStatus, String userId, String orderId, long credits) {
        this.id = UUID.randomUUID().toString();
        this.paymentStatus = paymentStatus;
        this.userId = userId;
        this.orderId = orderId;
        this.credits = credits;
    }

    /**
     * Constructor for existing payments from the database
     */
    public Payment(String id, PaymentStatus paymentStatus, String userId, String orderId, long credits) {
        this.id = id;
        this.paymentStatus = paymentStatus;
        this.userId = userId;
        this.orderId = orderId;
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getCredits() {
        return credits;
    }
}
