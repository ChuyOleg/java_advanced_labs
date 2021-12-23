package com.oleh.chui.model.service;

import com.oleh.chui.model.dao.ProductDao;
import com.oleh.chui.model.entity.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void sort(List<Product> productList, String sortField) {
        if (sortField != null && productList != null) {
            switch (sortField) {
                case "name":
                    sortByName(productList);
                    break;
                case "topPrice":
                    sortByPriceDescending(productList);
                    break;
                case "lowPrice":
                    sortByPriceAscending(productList);
                    break;
                case "date":
                    sortByDate(productList);
                    break;
            }
        }
    }

    private void sortByName(List<Product> productList) {
        productList.sort(Comparator.comparing(Product::getName));
    }

    private void sortByPriceAscending(List<Product> productList) {
        productList.sort(Comparator.comparing(Product::getPrice));
    }

    private void sortByPriceDescending(List<Product> productList) {
        productList.sort(Comparator.comparing(Product::getPrice).reversed());
    }

    private void sortByDate(List<Product> productList) {
        productList.sort(Comparator.comparing(Product::getStartDate).reversed());
    }

    public List<Product> filter(List<Product> productList, Map<String, String[]> filterMap) {
        String[] categories = filterMap.get("category[]");
        String[] sizes = filterMap.get("size[]");
        String minPriceString = filterMap.get("minPrice")[0];
        String maxPriceString = filterMap.get("maxPrice")[0];

        if (categories != null) {
            productList = filterByCategory(productList, categories);
        }
        if (sizes != null) {
            productList = filterBySize(productList, sizes);
        }
        if (!minPriceString.isEmpty() || !maxPriceString.isEmpty()) {
            BigDecimal minPrice = minPriceString.isEmpty() ? BigDecimal.valueOf(Integer.MIN_VALUE) : BigDecimal.valueOf(Double.parseDouble(minPriceString));
            BigDecimal maxPrice = maxPriceString.isEmpty() ? BigDecimal.valueOf(Integer.MAX_VALUE) : BigDecimal.valueOf(Double.parseDouble(maxPriceString));

            productList = filterByPriceRange(productList, minPrice, maxPrice);
        }

        return productList;
    }

    private List<Product> filterByCategory(List<Product> productList, String[] categories) {
        return productList.stream()
                .filter(product -> Arrays.asList(categories).contains(product.getCategory()))
                .collect(Collectors.toList());
    }

    private List<Product> filterByPriceRange(List<Product> productList, BigDecimal min, BigDecimal max) {
        return productList.stream()
                .filter(product -> product.getPrice().compareTo(min) >= 0 && product.getPrice().compareTo(max) <= 0)
                .collect(Collectors.toList());
    }

    private List<Product> filterBySize(List<Product> productList, String[] sizes) {
        return productList.stream()
                .filter(product -> Arrays.asList(sizes).contains(product.getSize().getValue()))
                .collect(Collectors.toList());
    }

    public void create(Product product) {
        productDao.create(product);
    }

    public Product findById(int id) {
        return productDao.findById(id).orElse(new Product());
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }
}
