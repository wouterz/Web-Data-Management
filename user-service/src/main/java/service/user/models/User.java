package service.user.models;

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

    public User(long id, long credits) {
        this.id = UUID.randomUUID().toString();
        this.credits = credits;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

}
