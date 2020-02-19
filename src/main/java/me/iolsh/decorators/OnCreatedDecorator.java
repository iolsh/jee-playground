package me.iolsh.decorators;

import me.iolsh.audit.AuditService;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class OnCreatedDecorator<T> implements OnCreated<T> {

    @Inject
    private AuditService auditService;

    @Inject
    @Delegate
    private OnCreated<T> delegate;

    @Override
    public void create(T entity) {
        delegate.create(entity);
        auditService.audit(entity);
    }
}
