package service.payment.models;

public class Payment {

    public enum PaymentStatus {
        PAID, UNPAID
    }

    private long id;
    private PaymentStatus paymentStatus;
    private long userId;
    private long orderId;
    private long credits;

    public Payment(long id, PaymentStatus paymentStatus, long userId, long orderId, long credits) {
        this.id = id;
        this.paymentStatus = paymentStatus;
        this.userId = userId;
        this.orderId = orderId;
        this.credits = credits;
    }

    public long getId() {
        return id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getCredits() {
        return credits;
    }
}
