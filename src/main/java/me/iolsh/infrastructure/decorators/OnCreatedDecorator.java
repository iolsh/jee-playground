package me.iolsh.infrastructure.decorators;

import me.iolsh.audit.AuditService;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class OnCreatedDecorator<T> implements OnCreated<T> {

    private final AuditService auditService;
    private final OnCreated delegate;

    @Inject
    public OnCreatedDecorator(AuditService auditService, @Delegate  OnCreated<T> delegate) {
        this.auditService = auditService;
        this.delegate = delegate;
    }

    @Override
    public T create(T entity) {
        delegate.create(entity);
        auditService.audit(entity);
        return entity;
    }
}
