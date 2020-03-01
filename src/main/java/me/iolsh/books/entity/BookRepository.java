package me.iolsh.books.entity;

import me.iolsh.infrastructure.monitoring.PerformanceMonitor;
import me.iolsh.infrastructure.jpa.AbstractHibernateRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;


@PerformanceMonitor
@ApplicationScoped
@Transactional(REQUIRED)
public class BookRepository extends AbstractHibernateRepository<Book, String> {

}
