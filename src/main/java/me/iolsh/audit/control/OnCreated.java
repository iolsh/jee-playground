package me.iolsh.audit.control;

public interface OnCreated<T> {
    T create(T entity);
}
