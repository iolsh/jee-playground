package me.iolsh.config;

import com.github.javafaker.Faker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

@ApplicationScoped
public class Producers {
    @Produces
    public Faker createFaker(final InjectionPoint injectionPoint) {
        return new Faker();
    }

    @Produces
    public Logger getLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }


}