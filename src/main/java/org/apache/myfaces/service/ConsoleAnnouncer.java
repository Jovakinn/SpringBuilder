package org.apache.myfaces.service;

public class ConsoleAnnouncer implements Announcer {
    private final Recommendator recommendator = ObjectFactory.getInstance().createObject(Recommendator.class);

    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}
