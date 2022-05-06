package org.apache.myfaces.service;

public class CoronaDesinfector {
    @InjectByType
    private final Announcer announcer;
    @InjectByType
    private final Policeman policeman;

    public void start(Room room) {
        announcer.announce("Need to get out of room");
        policeman.getOutPeople();
        desinfect(room);
        announcer.announce("Risk to come back. Can't see the COVID-19");
    }

    private void desinfect(Room room) {
        System.out.println("Covid go out !");
    }
}
