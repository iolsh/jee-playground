package me.iolsh.repository;

import me.iolsh.entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
@Transactional(REQUIRED)
public class BookRepository extends AbstractHibernateRepository<Book, String> {

}
