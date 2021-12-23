package com.oleh.chui.model.dao;

import com.oleh.chui.model.entity.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product>{

    List<Product> findProductListByPersonId(int id);

}
