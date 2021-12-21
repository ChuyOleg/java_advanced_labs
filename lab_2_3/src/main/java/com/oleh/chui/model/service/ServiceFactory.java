package com.oleh.chui.model.service;

public abstract class ServiceFactory {

    private static volatile ServiceFactory serviceFactory;

    public abstract ProductService createProductService();
    public abstract PersonService createPersonService();
    public abstract OrderingService createOrderingService();

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactoryImpl();
                }
            }
        }
        return serviceFactory;
    }

}
