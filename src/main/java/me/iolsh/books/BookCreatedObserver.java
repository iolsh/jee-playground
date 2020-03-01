package me.iolsh.books;

import com.zanox.rabbiteasy.Message;
import com.zanox.rabbiteasy.publisher.MessagePublisher;
import me.iolsh.infrastructure.rabbitmq.Publisher;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;

import java.io.IOException;

import static me.iolsh.infrastructure.rabbitmq.RabbitMqConfig.DEFAULT_FANOUT_EXCHANGE;

@ApplicationScoped
public class BookCreatedObserver {

    private final Logger logger;
    private final MessagePublisher publisher;

    @Inject
    public BookCreatedObserver(Logger logger, @Publisher MessagePublisher publisher) {
        this.logger = logger;
        this.publisher = publisher;
    }

    public void onBookCreated(@Observes(during = TransactionPhase.AFTER_SUCCESS) Book book) {
        Message message = new Message().exchange(DEFAULT_FANOUT_EXCHANGE).body("Book created:" + book );
        try {
            publisher.publish(message);
        } catch (IOException e) {
            logger.error("Unable to publish Book Created message {}", e);
        }
    }
}
