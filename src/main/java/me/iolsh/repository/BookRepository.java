package me.iolsh.repository;

import me.iolsh.entity.Book;
import me.iolsh.inerceptors.PerformanceMonitor;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;


@PerformanceMonitor
@ApplicationScoped
@Transactional(REQUIRED)
public class BookRepository extends AbstractHibernateRepository<Book, String> {

}
