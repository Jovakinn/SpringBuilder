package org.apache.myfaces.service;

public interface Config {
    <T> Class<? extends T> getImplType(Class<T> type);
}
