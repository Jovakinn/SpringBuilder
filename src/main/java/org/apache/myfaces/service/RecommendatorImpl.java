package org.apache.myfaces.service;

public class RecommendatorImpl implements Recommendator {

    @InjectProperty("alcohol")
    private String drinkName;

    @Override
    public void recommend() {
        System.out.println("drink " + drinkName);
    }
}
