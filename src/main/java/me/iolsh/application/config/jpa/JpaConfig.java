package me.iolsh.application.config.jpa;

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

    @PersistenceUnit
    EntityManagerFactory emf;

    @PersistenceContext
    EntityManager entityManager;

    @Produces @ContainerManaged @RequestScoped
    public EntityManager createEntityManager() {
        EntityManager em = this.entityManager;
        logger.info("Providing container managed EntityManager {} ...", em);
        return em;
    }

    @Produces @ApplicationManaged @RequestScoped
    public EntityManager createApplicationManagedEntityManger() {
        EntityManager em = emf.createEntityManager();
        logger.info("Providing application managed EntityManager {} ...", em);
        return em;
    }

    public void logDisposingCMEM(@Disposes @ContainerManaged EntityManager em) {
        logger.debug("Disposing container managed EntityManager: {}", em);
    }

    public void closeEntityManager(@Disposes @ApplicationManaged EntityManager em) {
        logger.info("Closing application managed EntityManager: {}", em);
        if (em.isOpen()) {
            em.close();
        }
    }

}
