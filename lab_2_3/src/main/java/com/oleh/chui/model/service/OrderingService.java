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

    public List<Ordering> findAllByPersonId(int personId) {
        return orderingDao.findAllByPersonId(personId);
    }

    public void update(Ordering ordering) {
        orderingDao.update(ordering);
    }

    public void delete(int id) {
        orderingDao.delete(id);
    }

    public void changeStatusById(int id, Ordering.Status status) {
        if (status.equals(Ordering.Status.PAID)) {
            changeStatusToPaidById(id);
        } else if (status.equals(Ordering.Status.CANCELED)) {
            changeStatusToCanceledById(id);
        }
    }

    private void changeStatusToPaidById(int id) {
        orderingDao.changeStatusToPaidById(id);
    }

    private void changeStatusToCanceledById(int id) {
        orderingDao.changeStatusToCanceledById(id);
    }

    public Ordering buildStandardOrderingWithoutId(int productId, int personId) {
        return Ordering.builder()
                .productId(productId)
                .personId(personId)
                .status(Ordering.Status.REGISTERED)
                .build();
    }
}
