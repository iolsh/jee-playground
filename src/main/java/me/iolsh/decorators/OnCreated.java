package me.iolsh.decorators;

public interface OnCreated<T> {
    void create(T entity);
}
