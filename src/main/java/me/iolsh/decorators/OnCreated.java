package me.iolsh.decorators;

public interface OnCreated<T> {
    T create(T entity);
}
