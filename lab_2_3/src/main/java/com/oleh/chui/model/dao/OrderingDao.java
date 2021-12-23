package com.oleh.chui.model.dao;

import com.oleh.chui.model.entity.Ordering;

public interface OrderingDao extends GenericDao<Ordering> {

    void changeStatusToPaidById(int id);

    void changeStatusToCanceledById(int id);

    Ordering.Status findStatusByProductIdAndPersonId(int productId, int personId);

    boolean isExistByProductIdAndPersonId(int productId, int personId);

}
