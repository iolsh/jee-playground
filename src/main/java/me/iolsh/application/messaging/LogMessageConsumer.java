package me.iolsh.application.messaging;

import com.zanox.rabbiteasy.Message;
import com.zanox.rabbiteasy.consumer.ConsumerContainer;
import com.zanox.rabbiteasy.consumer.MessageConsumer;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;

public class LogMessageConsumer extends MessageConsumer {

    @Inject
    Logger logger;

    @Inject
    ConsumerContainer consumerContainer;

    @Override
    public void handleMessage(Message message) {
        logger.info("[√] Got new message: {}", message.getBodyAs(String.class));
        logger.debug("RoutingKey tag: {}", message.getRoutingKey());
        logger.debug("Delivery tag: {}", message.getDeliveryTag());
        logger.debug("Exchange: {}", message.getExchange());
        logger.debug("Properties: {}", message.getBasicProperties());
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
