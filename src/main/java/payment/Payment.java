package payment;

public class Payment {
	
    private final long userId;
    private final long orderId;
    private boolean isPayed;

    public Payment(long userId, long orderId){
        this.userId = userId;
        this.orderId = orderId;
        this.isPayed = false;
    }

    public boolean getPayStatus() {
    	return this.isPayed;
    }
}
