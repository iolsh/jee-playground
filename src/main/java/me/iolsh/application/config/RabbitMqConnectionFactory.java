package me.iolsh.application.config;

import com.rabbitmq.client.ConnectionFactory;

import javax.enterprise.inject.Produces;

public class RabbitMqConnectionFactory {

    private static final String HOST = ConnectionFactory.DEFAULT_HOST;
    private static final int PORT = ConnectionFactory.DEFAULT_AMQP_PORT;
    private static final String USER = "playground";
    private static final String PASSWORD = "playground";

    @Produces
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setPort(PORT);
        return factory;
    }

}
