package com.oleh.chui.model.dao.impl.query;

public class ProductQueries {

    public static final String CREATE = "INSERT INTO product(name, price, category, startDate, size)" +
            " values(?, ?, ?, ?, ?)";

    public static final String FIND_BY_ID = "SELECT * FROM product WHERE id = ?";

    public static final String FIND_ALL = "SELECT * FROM product";

    public static final String FIND_PRODUCT_LIST_BY_PERSON_ID = "SELECT productId as id, name, price, category" +
            ", startDate, size FROM product INNER JOIN ordering ON (product.id = ordering.productId) where personId = ?";

    public static final String UPDATE = "UPDATE product SET name = ?, price = ?, category = ?," +
            " startDate = ?, size = ? WHERE id = ?";

    public static final String DELETE = "DELETE FROM product WHERE id = ?";

}
