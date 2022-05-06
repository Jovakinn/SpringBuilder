package org.apache.myfaces.service;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private final Map<String, String> map;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        var path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("application.properties")).getPath();
        var lines = new BufferedReader(new FileReader(path)).lines();
        map = lines.map(line -> line.split("="))
                .collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object t) {
        Class<?> type = t.getClass();
        for (var field : type.getDeclaredFields()) {
            var annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                var propertyName = annotation.value();
                var propertyValue = map.get(propertyName);
                field.setAccessible(true);
                field.set(t, propertyValue);
            }
        }
    }
}
