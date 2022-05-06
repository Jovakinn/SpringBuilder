package org.apache.myfaces.service;

import lombok.AllArgsConstructor;
import org.reflections.Reflections;

import java.util.Map;
@AllArgsConstructor
public class JavaConfig implements Config {

    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    @Override
    public <T> Class<? extends T> getImplType(Class<T> type) {
        return ifc2ImplClass.computeIfAbsent(type, aClass -> {
            var set = scanner.getSubTypesOf(type);
            if (set.size() != 1){
                throw new IllegalArgumentException(type + " has 0 or more than one impl");
            }
            return set.iterator().next();
        });

    }
}
