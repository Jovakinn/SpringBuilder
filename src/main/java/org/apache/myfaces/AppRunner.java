package org.apache.myfaces;

import lombok.SneakyThrows;
import org.apache.myfaces.service.*;

import java.util.HashMap;
import java.util.Map;

public class AppRunner {
    @SneakyThrows
    public static void main(String[] args) {
        HashMap<Class, Class> map = new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class));
        ApplicationContext context = ApplicationRunner.run("org.apache", map);
        context.getObject(CoronaDesinfector.class).start(new Room());
    }
}
