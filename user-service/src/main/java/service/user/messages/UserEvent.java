package service.user.messages;

import service.user.User;

public class UserEvent {

    public enum Action {
        Create,
        Remove,
        Get,
        SubtractCredits,
        AddCredits,
    }

    public UserEvent(User u) {
//        super(s);

        User user = u;

    }
}
