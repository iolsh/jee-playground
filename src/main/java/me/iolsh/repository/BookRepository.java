package me.iolsh.repository;

import me.iolsh.entity.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
@Transactional(REQUIRED)
public class BookRepository extends AbstractHibernateRepository<Book, String> {

}
