package me.iolsh.repository;

import me.iolsh.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
@Transactional(REQUIRED)
public class UserRepository {

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager entityManager;

    public void create(final User user) {
        logger.log(Level.INFO, "Persisting user {0}", user);
        entityManager.persist(user);
    }

    public Optional<User> findUserByUserName(String userName) {
        User user = entityManager.createNamedQuery(User.FIND_USER_BY_USER_NAME, User.class)
                .setParameter("userName", userName).getSingleResult();
        return Optional.ofNullable(user);
    }

}
