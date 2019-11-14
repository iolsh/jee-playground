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
public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(final Book book) {
        entityManager.persist(book);
    }

    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

}
