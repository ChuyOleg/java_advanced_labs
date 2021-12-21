package com.oleh.chui.model.dao.impl;

import com.oleh.chui.model.dao.DaoFactory;
import com.oleh.chui.model.dao.OrderingDao;
import com.oleh.chui.model.dao.PersonDao;
import com.oleh.chui.model.dao.ProductDao;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public ProductDao createProductDao() {
        return new ProductDaoImpl();
    }

    @Override
    public PersonDao createPersonDao() {
        return new PersonDaoImpl();
    }

    @Override
    public OrderingDao createOrderingDao() {
        return new OrderingDaoImpl();
    }
}
