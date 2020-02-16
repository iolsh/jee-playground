package me.iolsh.application.messaging;

import com.zanox.rabbiteasy.Message;
import com.zanox.rabbiteasy.publisher.MessagePublisher;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class TickMessageProducer {

    private static final String EXCHANGE_NAME = "playground.fanout";

    private AtomicInteger counter = new AtomicInteger();

    @Inject
    Logger logger;

    @Inject
    MessagePublisher publisher;

    public void tick() {
        Message message = new Message().exchange(EXCHANGE_NAME).body("Tick: " + counter.getAndIncrement());
        try {
            publisher.publish(message);
            publisher.close();
        } catch (IOException e) {
            logger.error("Unable to publish message, {}", e);
        }
    }


}
