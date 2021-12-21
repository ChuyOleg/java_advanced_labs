package com.oleh.chui.model.service;

import com.oleh.chui.model.dao.OrderingDao;
import com.oleh.chui.model.entity.Ordering;

import java.util.List;

public class OrderingService {

    private final OrderingDao orderingDao;

    public OrderingService(OrderingDao orderingDao) {
        this.orderingDao = orderingDao;
    }

    public void create(Ordering ordering) {
        orderingDao.create(ordering);
    }

    public Ordering findById(int id) {
        return orderingDao.findById(id).orElse(new Ordering());
    }

    public List<Ordering> findAll() {
        return orderingDao.findAll();
    }

    public void update(Ordering ordering) {
        orderingDao.update(ordering);
    }

    public void delete(int id) {
        orderingDao.delete(id);
    }

    public void changeStatusToPaidById(int id) {
        orderingDao.changeStatusToPaidById(id);
    }

    public void changeStatusToCanceledById(int id) {
        orderingDao.changeStatusToCanceledById(id);
    }
}
