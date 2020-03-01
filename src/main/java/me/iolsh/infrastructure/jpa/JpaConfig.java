package me.iolsh.infrastructure.jpa;

import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class JpaConfig {

    private final Logger logger;

    @Inject
    public JpaConfig(Logger logger) {
        this.logger = logger;
    }

    @PersistenceContext
    EntityManager entityManager;

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Produces @ContainerManaged @RequestScoped
    public EntityManager createEntityManager() {
        EntityManager em = this.entityManager;
        logger.debug("Providing container managed EntityManager {} ...", em);
        return em;
    }

    @Produces @ApplicationManaged @RequestScoped
    public EntityManager createApplicationManagedEntityManger() {
        EntityManager em = entityManagerFactory.createEntityManager();
        logger.debug("Providing application managed EntityManager {} ...", em);
        return em;
    }

    public void logDisposingEntityManager(@Disposes @ContainerManaged EntityManager em) {
        logger.debug("Disposing container managed EntityManager: {}", em);
    }

    public void closeEntityManager(@Disposes @ApplicationManaged EntityManager em) {
        logger.debug("Closing application managed EntityManager: {}", em);
        if (em.isOpen()) {
            em.close();
        }
    }

}
