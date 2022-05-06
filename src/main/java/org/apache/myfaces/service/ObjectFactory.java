package org.apache.myfaces.service;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {
    private static final ObjectFactory instance = new ObjectFactory();
    private final Config config;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    @SneakyThrows
    private ObjectFactory() {
        var scanner = new Reflections("org.apache");
        Map<Class, Class> map = new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class));
        config = new JavaConfig(scanner, map);
        var classes = scanner.getSubTypesOf(ObjectConfigurator.class);
        for (var aClass : classes) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    public static ObjectFactory getInstance() {
        return instance;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        type = resolveImpl(type);
        var t = type.getDeclaredConstructor().newInstance();
        configure(t);

        return t;
    }

    private <T> Class<T> resolveImpl(Class<T> type) {
        if (type.isInterface()) {
            type = (Class<T>)config.getImplType(type);
        }
        return type;
    }

    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configure(t));
    }
}
