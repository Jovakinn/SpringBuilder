package org.apache.myfaces.service;

@Singleton
public class RecommendatorImpl implements Recommendator {

    @InjectProperty("alcohol")
    private String drinkName;

    public RecommendatorImpl() {
        System.out.println("recommendator was created");
    }

    @Override
    public void recommend() {
        System.out.println("drink " + drinkName);
    }
}
