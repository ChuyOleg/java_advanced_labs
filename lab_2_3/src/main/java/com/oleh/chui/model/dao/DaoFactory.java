package com.oleh.chui.model.dao;

import com.oleh.chui.model.dao.impl.DaoFactoryImpl;

public abstract class DaoFactory {

    private static volatile DaoFactory daoFactory;

    public abstract ProductDao createProductDao();
    public abstract PersonDao createPersonDao();
    public abstract OrderingDao createOrderingDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new DaoFactoryImpl();
                }
            }
        }
        return daoFactory;
    }

}
