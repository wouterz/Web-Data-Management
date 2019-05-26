package service.order.models;

public class User {


    private final Long id;
    private long credits;

    public long getId() {
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
