package me.iolsh.application.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ApplicationScoped
public class PlaygroundMessageProducer {

    private static final String EXCHANGE_NAME = "playground.fanout";
    private static final String EXCHANGE_TYPE = "fanout";

    @Inject
    Logger logger;

    @Inject @Named("connectionFactory")
    ConnectionFactory factory;

    public void message(String message)  {
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            logger.info("[x] Sent: {}", message);
        } catch (Exception e) {
            logger.error("Unable to deliver message {}", e);
        }
    }
}
