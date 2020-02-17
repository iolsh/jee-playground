package me.iolsh.application.config;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

public class Config {

    @Produces
    public Faker createFaker() {
        return new Faker();
    }

    @Produces
    public Logger getLogger(final InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Provider  //TODO check it
    public static class JsonbConfigurator implements ContextResolver<Jsonb> {
        @Override
        public Jsonb getContext(Class<?> type) {
            JsonbConfig config = new JsonbConfig().withNullValues(false);
            return JsonbBuilder.newBuilder().withConfig(config).build();
        }
    }
}