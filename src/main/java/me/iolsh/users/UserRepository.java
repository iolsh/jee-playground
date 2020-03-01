package me.iolsh.users;

import me.iolsh.infrastructure.jpa.AbstractHibernateRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Optional;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
@Transactional(REQUIRED)
public class UserRepository extends AbstractHibernateRepository<User, Long> {

    public Optional<User> findUserByUserName(String userName) {
        return getNamedQuery(User.FIND_USER_BY_USER_NAME)
            .setParameter("userName", userName).getResultStream().findFirst();
    }


}
