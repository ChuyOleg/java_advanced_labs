package com.oleh.chui.model.service.util;

import com.oleh.chui.model.entity.Ordering;
import com.oleh.chui.model.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilService {

    public static Map<Integer, Product> getProductIdMapByOrderingId(List<Product> productList, List<Ordering> orderingList) {
        Map<Integer, Product> map = new HashMap<>();
        orderingList.forEach(ordering -> map.put(ordering.getId(), productList.stream()
                .filter(product -> product.getId() == ordering.getProductId()).findAny().orElse(new Product())));

        return map;
    }

}
