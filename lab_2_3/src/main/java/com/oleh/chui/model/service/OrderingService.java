package com.oleh.chui.model.service;

import com.oleh.chui.model.dao.OrderingDao;
import com.oleh.chui.model.entity.Ordering;
import com.oleh.chui.model.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Integer, Ordering.Status> getStatusMapByPersonIdFromProductList(int personId, List<Product> productList) {
        Map<Integer, Ordering.Status> statusMap = new HashMap<>();
        productList.forEach(product -> {
            statusMap.put(product.getId(), orderingDao.findStatusByProductIdAndPersonId(product.getId(), personId));
        });
        return statusMap;
    }


    // TODO
    // think about deleting
    public List<Integer> getListOfOrderedProductsIdByPersonId(int personId, List<Product> productList) {
        List<Integer> listOfOrderedProductsId = new ArrayList<>();
        productList.forEach(product -> {
            if (orderingDao.isExistByProductIdAndPersonId(product.getId(), personId)) {
                listOfOrderedProductsId.add(product.getId());
            }
        });

        return listOfOrderedProductsId;
    }

    public Ordering buildStandardOrderingWithoutId(int productId, int personId) {
        return Ordering.builder()
                .productId(productId)
                .personId(personId)
                .status(Ordering.Status.REGISTERED)
                .build();
    }
}
