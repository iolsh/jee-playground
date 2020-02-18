package me.iolsh.application;

import com.github.javafaker.Faker;
import me.iolsh.messaging.LogMessageConsumer;
import me.iolsh.messaging.TickMessageProducer;
import me.iolsh.entity.Book;
import me.iolsh.repository.BookRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.UUID;
import java.util.stream.IntStream;

@Startup
@Singleton
public class OnApplicationStartup {

    @Inject
    private Faker faker;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private LogMessageConsumer logMessageConsumer;

    @Inject
    private TickMessageProducer tickMessageProducer;

    @Inject
    @ConfigProperty(name = "rabbitmq.queue")
    private String queueName;

    @PostConstruct
    public void init() {
        populateBooksInDatabase();
        logMessageConsumer.start(queueName);
    }

    @Lock(LockType.READ)
    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void tickMessage() {
        tickMessageProducer.tick();
    }


    private void populateBooksInDatabase() {
        IntStream.range(1,10).forEach(this::createBook);
    }

    private void createBook(int i) { //TODO check how to get rid of r
        bookRepository.create(randomBook());
    }
    
    private Book randomBook() {
        return Book.builder()
                .author(faker.book().author())
                .title(faker.book().title())
                .genre(faker.book().genre())
                .year(faker.number().numberBetween(1900, 2019))
                .build();
    }
}
