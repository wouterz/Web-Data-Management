package service.user.messages;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class IncommingMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();

//        if (payload instanceof OrderEvent) {
//            handleNewOrder((OrderEvent) payload);
//        } else {
//            throw new MessageRejectedException(message, "Unknown data type has been received.");
//        }
    }

//    void handleNewOrder(OrderEvent event) {
//        if (event.action == OrderEvent.Action.Checkout) {
//            User user = User.find(event.order.getUserId());
//
//            long totalAmount = 1;
//            user.setCredits(user.getCredits() - totalAmount);
//        }
//    }
}

