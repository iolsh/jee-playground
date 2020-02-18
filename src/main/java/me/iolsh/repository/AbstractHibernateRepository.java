package me.iolsh.repository;

import org.hibernate.Session;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractHibernateRepository<T, I> {

    private final Class<T> persistentClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Event<T> entityCreatedEvent;

    public AbstractHibernateRepository() {
        Type genericSuperClass = getClass().getGenericSuperclass();
        ParameterizedType parametrizedType = null;
        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }
        this.persistentClass = (Class<T>) parametrizedType.getActualTypeArguments()[0];

    }

    public List<T> findAll() {
        CriteriaBuilder cb = getSession().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(persistentClass);
        Root<T> rootEntry = cq.from(persistentClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = getSession().createQuery(all);
        return allQuery.getResultList();
    }


    public Optional<T> findById(I id) {
        T entity = entityManager.find(persistentClass, id);
        return Optional.ofNullable(entity);
    }

    public T getOne(I id) {
        return findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entity %s with id % has not been found.",
                        persistentClass.getName(), id)));
    }

    public void create(T entity) {
        entityManager.persist(entity);
        entityCreatedEvent.fire(entity);
    }

    protected TypedQuery<T> getNamedQuery(String query) {
        return entityManager.createNamedQuery(query, persistentClass);
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

}