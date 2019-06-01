package service.user;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import service.user.models.User;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private Processor processor;
    @Autowired
    private MessageCollector messageCollector;

/*
    @Test
    @SuppressWarnings("unchecked")
    public void testProcessing() {
        User o = new User(123, 321111);
        processor.input().send(MessageBuilder.withPayload(o).build());
        Message<Object> received = (Message<Object>) messageCollector.forChannel(processor.output()).poll();
        LOGGER.info("Order response received: {}", received.getPayload());
        assertNotNull(received.getPayload());
    }*/

}
