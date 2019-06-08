package service.stock.models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;


    private final String id;
    private long credits;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", credits=" + credits +
                '}';
    }

    /**
     * Constructor for new users
     */
    public User() {
        this.id = UUID.randomUUID().toString();
        this.credits = 0;
    }

    /**
     * Constructor for users retrieved from the dataabse
     * @param id Id of the user
     * @param credits Credit of the user
     */
    public User(String id, long credits) {
        this.id = id;
        this.credits = credits;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

}
