package me.iolsh.application.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageProducer {

    private static final String EXCHANGE_NAME = "playground.fanout";
    private static final String EXCHANGE_TYPE = "fanout";
    private static final String HOST = ConnectionFactory.DEFAULT_HOST;
    private static final int PORT = ConnectionFactory.DEFAULT_AMQP_PORT;
    private static final String USER = "playground";
    private static final String PASSWORD = "playground";

    public void message(String message)  {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setPort(PORT);
        try (
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE, true);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
