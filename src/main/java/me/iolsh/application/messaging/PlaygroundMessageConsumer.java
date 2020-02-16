package me.iolsh.application.messaging;

import com.rabbitmq.client.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PlaygroundMessageConsumer {

    private static final String QUEUE_NAME = "playground";

    @Inject
    Logger logger;

    @Inject
    ConnectionFactory factory;

    public void consume()  {
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body) throws IOException {
                    logger.info("[√] Got new message: {}", new String(body, "UTF-8"));
                    logger.debug("Consumer tag: {}", consumerTag);
                    logger.debug("Envelope: {}", envelope);
                    logger.debug("Properties: {}", properties);
                }
            };

            channel.basicConsume(QUEUE_NAME, true, consumer);

        } catch (TimeoutException | IOException e) {
            logger.error("Unable to consume messages {}", e);
        }
    }
}
