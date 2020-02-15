package me.iolsh.application.config;

import com.github.javafaker.Faker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@ApplicationScoped
public class Config {

    @Produces
    public Faker createFaker(final InjectionPoint injectionPoint) {
        return new Faker();
    }

    @Produces
    public Logger getLogger(final InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Provider  //TODO check if works here
    public static class JsonbConfigurator implements ContextResolver<Jsonb> {
        @Override
        public Jsonb getContext(Class<?> type) {
            JsonbConfig config = new JsonbConfig().withNullValues(false);
            return JsonbBuilder.newBuilder().withConfig(config).build();
        }
    }
}