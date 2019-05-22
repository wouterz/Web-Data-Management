package service.user;

public class User {


    private final Long id;
    private long credits;

    public long getId() {
        return id;
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
