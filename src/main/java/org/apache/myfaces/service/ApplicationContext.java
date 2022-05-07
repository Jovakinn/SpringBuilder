package org.apache.myfaces.service;

import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private final Reflections scanner;
    private final JavaConfig config;
    private final ObjectFactory factory;

    public ApplicationContext(String packagesToScan, Map<Class, Class> ifc2Impl) {
        scanner = new Reflections(packagesToScan);
        config = new JavaConfig(scanner, ifc2Impl);
        factory = new ObjectFactory(this);
    }

    @SneakyThrows
    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        var implCLass = resolveImpl(type);
        T t = factory.createObject(implCLass);

        if (implCLass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }
        return t;
    }

    private <T> Class<T> resolveImpl(Class<T> type) {
        if (type.isInterface()) {
            type = (Class<T>)config.getImplType(type);
        }
        return type;
    }
}
