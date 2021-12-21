package com.oleh.chui.model.dao;

import com.oleh.chui.model.entity.Ordering;

public interface OrderingDao extends GenericDao<Ordering> {

    void changeStatusToPaidById(int id);

    void changeStatusToCanceledById(int id);

}
