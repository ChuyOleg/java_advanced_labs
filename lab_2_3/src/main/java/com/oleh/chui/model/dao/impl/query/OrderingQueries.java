package com.oleh.chui.model.dao.impl.query;


public class OrderingQueries {

    public static final String CREATE = "INSERT INTO ordering(productId, personId, status)" +
            " values(?, ?, ?)";

    public static final String FIND_BY_ID = "SELECT * FROM ordering WHERE id = ?";

    public static final String FIND_ALL = "SELECT * FROM ordering";

    public static final String UPDATE = "UPDATE ordering SET productId = ?, personId = ?, status = ? WHERE id = ?";

    public static final String DELETE = "DELETE FROM ordering WHERE id = ?";

}
