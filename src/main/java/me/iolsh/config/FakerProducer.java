package me.iolsh.config;

import com.github.javafaker.Faker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class FakerProducer {
    @Produces
    public Faker createFaker(final InjectionPoint injectionPoint) {
        return new Faker();
    }
}