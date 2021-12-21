package com.oleh.chui.model.service;

import com.oleh.chui.model.dao.DaoFactory;

public class ServiceFactoryImpl extends ServiceFactory{
    @Override
    public ProductService createProductService() {
        return new ProductService(DaoFactory.getInstance().createProductDao());
    }

    @Override
    public PersonService createPersonService() {
        return new PersonService(DaoFactory.getInstance().createPersonDao());
    }

    @Override
    public OrderingService createOrderingService() {
        return new OrderingService(DaoFactory.getInstance().createOrderingDao());
    }
}
