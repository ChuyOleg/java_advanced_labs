package com.oleh.chui.model.dao;

import com.oleh.chui.model.entity.Ordering;

import java.util.List;

public interface OrderingDao extends GenericDao<Ordering> {

    void changeStatusToPaidById(int id);

    void changeStatusToCanceledById(int id);

    List<Ordering> findAllByPersonId(int personId);

    Ordering.Status findStatusByProductIdAndPersonId(int productId, int personId);

    boolean isExistByProductIdAndPersonId(int productId, int personId);

}
