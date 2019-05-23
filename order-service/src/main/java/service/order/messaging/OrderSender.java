package service.user.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import service.user.models.User;

@Service
public class OrderSender {

    @Autowired
    private Source source;

    public boolean send(User user) {
        return this.source.output().send(MessageBuilder.withPayload(user).build());
    }
}
