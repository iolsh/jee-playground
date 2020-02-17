package me.iolsh.application.config.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.zanox.rabbiteasy.SingleConnectionFactory;
import com.zanox.rabbiteasy.consumer.ConsumerContainer;
import com.zanox.rabbiteasy.publisher.ConfirmedPublisher;
import com.zanox.rabbiteasy.publisher.MessagePublisher;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class RabbitMqConfig {

    public static final String DEFAULT_FANOUT_EXCHANGE= "amq.fanout";

    @Inject
    private Logger logger;
    @Inject
    @ConfigProperty(name = "rabbitmq.host")
    private String host;
    @Inject
    @ConfigProperty(name = "rabbitmq.port")
    private int port;
    @Inject
    @ConfigProperty(name = "rabbitmq.user")
    private String user;
    @Inject
    @ConfigProperty(name = "rabbitmq.password")
    private String password;


    @Produces @DefaultConnectionFactory
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setPassword(password);
        return factory;
    }

    @Produces @OneConnectionFactory
    public ConnectionFactory singleConnectionFactory() {
        ConnectionFactory factory = new SingleConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setPassword(password);
        return factory;
    }

    @Produces @Container
    public ConsumerContainer consumerContainer() {
        logger.info("Creating new ConsumerContainer...");
        return new ConsumerContainer(singleConnectionFactory());
    }

    @Produces @Publisher
    public MessagePublisher publisher() {
       logger.info("Creating new ConfirmedPublisher...");
       return new ConfirmedPublisher(singleConnectionFactory());
    }

    public void disableConsumerContainer(@Disposes @Container ConsumerContainer consumerContainer) {
        logger.info("Stopping Consumers...");
        consumerContainer.stopAllConsumers();
    }

    public void closePublisher(@Disposes @Publisher MessagePublisher messagePublisher) {
        try {
            logger.info("Closing MessagePublisher...");
            messagePublisher.close();
        } catch (IOException e) {
            logger.error("Unable to close MessagePublisher {}", e);
        }
    }
}
