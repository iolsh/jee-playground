package me.iolsh.infrastructure.decorators;

public interface OnCreated<T> {
    T create(T entity);
}
