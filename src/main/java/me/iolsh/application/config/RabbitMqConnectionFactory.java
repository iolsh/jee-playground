package me.iolsh.application.config;

import com.rabbitmq.client.ConnectionFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class RabbitMqConnectionFactory {

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

    @Produces
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(user);
        factory.setPassword(password);
        return factory;
    }

}
