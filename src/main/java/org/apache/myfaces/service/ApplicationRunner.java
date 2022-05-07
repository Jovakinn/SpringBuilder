package org.apache.myfaces.service;

import java.util.Map;

public class ApplicationRunner {
    public static ApplicationContext run(String packagesToScan, Map<Class, Class> ifcToImpl){
        return new ApplicationContext(packagesToScan, ifcToImpl);
    }
}
