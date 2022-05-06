package org.apache.myfaces;

import org.apache.myfaces.service.CoronaDesinfector;
import org.apache.myfaces.service.Room;

public class AppRunner {
    public static void main(String[] args) {
        var coronaDesinfector = new CoronaDesinfector();
        coronaDesinfector.start(new Room());
    }
}
