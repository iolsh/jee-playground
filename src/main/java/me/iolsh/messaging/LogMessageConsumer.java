package me.iolsh.messaging;

import com.zanox.rabbiteasy.Message;
import com.zanox.rabbiteasy.consumer.ConsumerContainer;
import com.zanox.rabbiteasy.consumer.MessageConsumer;
import me.iolsh.application.config.rabbitmq.Container;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;

public class LogMessageConsumer extends MessageConsumer {

    private final Logger logger;
    private final ConsumerContainer consumerContainer;

    @Inject
    public LogMessageConsumer(Logger logger, @Container ConsumerContainer consumerContainer) {
        this.logger = logger;
        this.consumerContainer = consumerContainer;
    }

    @Override
    public void handleMessage(Message message) {
        logger.info("[√] Got new message: {}", message.getBodyAs(String.class));
        logger.info("Properties: {}", message.getBasicProperties());
        logger.debug("Routing key: {}", message.getRoutingKey());
        logger.debug("Delivery tag: {}", message.getDeliveryTag());
        logger.debug("Exchange: {}", message.getExchange());
    }

    public void start(String queueName) {
        consumerContainer.addConsumer(this, queueName, true);
        try {
            consumerContainer.startConsumers(LogMessageConsumer.class);
        } catch (IOException e) {
            logger.error("Unable to consume messages: {}", e);
        }
    }
}
