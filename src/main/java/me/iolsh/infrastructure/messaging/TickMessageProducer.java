package me.iolsh.infrastructure.messaging;

import com.zanox.rabbiteasy.Message;
import com.zanox.rabbiteasy.publisher.MessagePublisher;
import me.iolsh.infrastructure.rabbitmq.Publisher;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static me.iolsh.infrastructure.rabbitmq.RabbitMqConfig.DEFAULT_FANOUT_EXCHANGE;

public class TickMessageProducer {

    private AtomicInteger counter = new AtomicInteger();

    private final Logger logger;
    private final MessagePublisher publisher;

    @Inject
    public TickMessageProducer(Logger logger, @Publisher MessagePublisher publisher) {
        this.logger = logger;
        this.publisher = publisher;
    }

    public void tick() {
        Message message = new Message().exchange(DEFAULT_FANOUT_EXCHANGE).body("Tick: " + counter.getAndIncrement());
        try {
            publisher.publish(message);
        } catch (IOException e) {
            logger.error("Unable to publish message, {}", e);
        }
    }


}
