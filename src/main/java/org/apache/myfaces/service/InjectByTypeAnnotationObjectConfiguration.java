package org.apache.myfaces.service;

import lombok.SneakyThrows;

public class InjectByTypeAnnotationObjectConfiguration implements ObjectConfigurator {

    @SneakyThrows
    @Override
    public void configure(Object t, ApplicationContext context) {
        for (var field : t.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(InjectByType.class)) {
                var object = context.getObject(field.getType());
                field.setAccessible(true);
                field.set(t, object);
            }
        }
    }
}
