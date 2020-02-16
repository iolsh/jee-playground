package me.iolsh.application.config;

import com.rabbitmq.client.ConnectionFactory;
import com.zanox.rabbiteasy.SingleConnectionFactory;
import com.zanox.rabbiteasy.consumer.ConsumerContainer;
import com.zanox.rabbiteasy.publisher.ConfirmedPublisher;
import com.zanox.rabbiteasy.publisher.MessagePublisher;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

public class RabbitMqConfig {

    public static final String DEFAULT_FANOUT_EXCHANGE= "amq.fanout";

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

    @Produces @Named("connectionFactory")
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setPassword(password);
        return factory;
    }

    @Produces @Named("singleConnectionFactory")
    public ConnectionFactory singleConnectionFactory() {
        ConnectionFactory factory = new SingleConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setPassword(password);
        return factory;
    }

    @Produces
    private ConsumerContainer consumerContainer() {
        return new ConsumerContainer(singleConnectionFactory());
    }

    @Produces
    public MessagePublisher publisher() {
       return new ConfirmedPublisher(singleConnectionFactory());
    }





}
