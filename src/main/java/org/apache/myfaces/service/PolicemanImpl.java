package org.apache.myfaces.service;

import javax.annotation.PostConstruct;

@Deprecated
public class PolicemanImpl implements Policeman{
    @InjectByType
    private Recommendator recommendator;

    @PostConstruct
    public void init() {
        System.out.println(recommendator.getClass());
    }

    @Override
    public void getOutPeople() {
        System.out.println("ALL GET OUT OF ROOM PUF PUF!!!");
    }
}
