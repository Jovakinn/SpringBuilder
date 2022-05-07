package org.apache.myfaces.service;

import lombok.SneakyThrows;
import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final List<ObjectConfigurator> configurators = new ArrayList<>();
    private final ApplicationContext context;

    @SneakyThrows
    ObjectFactory(ApplicationContext context) {
        this.context = context;
        //Map<Class, Class> map = ;
        var classes = context.getScanner().getSubTypesOf(ObjectConfigurator.class);
        for (var aClass : classes) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }


    public <T> T createObject(Class<T> type) throws Exception {
        var t = type.getDeclaredConstructor().newInstance();
        configure(t);

        return t;
    }

    private <T> T checkDeprecated(Class<T> type, T t) {
        if (type.isAnnotationPresent(Deprecated.class)) {
            return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(t, args);
                }
            });
        }
        return null;
    }

    private <T> void invokeInit(Class<T> type, T t) throws IllegalAccessException, InvocationTargetException {
        for (var method : type.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configure(t, context));
    }
}
