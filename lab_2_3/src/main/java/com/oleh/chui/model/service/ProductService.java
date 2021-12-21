package com.oleh.chui.model.service;

import com.oleh.chui.model.dao.ProductDao;
import com.oleh.chui.model.entity.Product;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void sortByName(List<Product> products) {
        products.sort(Comparator.comparing(Product::getName));
    }

    public void sortByPriceAscending(List<Product> products) {
        products.sort(Comparator.comparing(Product::getPrice));
    }

    public void sortByPriceDescending(List<Product> products) {
        products.sort(Comparator.comparing(Product::getPrice).reversed());
    }

    public void sortByDate(List<Product> products) {
        products.sort(Comparator.comparing(Product::getStartDate).reversed());
    }

    public List<Product> filterByCategory(List<Product> products, String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> filterByPriceRange(List<Product> products, BigDecimal min, BigDecimal max) {
        return products.stream()
                .filter(product -> product.getPrice().compareTo(min) >= 0 && product.getPrice().compareTo(max) <= 0)
                .collect(Collectors.toList());
    }

    public List<Product> filterBySize(List<Product> products, Product.Size size) {
        return products.stream()
                .filter(product -> product.getSize().equals(size))
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
