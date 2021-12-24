package com.oleh.chui.model.dao.impl.query;


import com.oleh.chui.model.entity.Ordering;

public class OrderingQueries {

    public static final String CREATE = "INSERT INTO ordering(productId, personId, status)" +
            " values(?, ?, ?)";

    public static final String FIND_BY_ID = "SELECT * FROM ordering WHERE id = ?";

    public static final String FIND_ALL_BY_PERSON_ID = "SELECT * FROM ordering WHERE personId = ?";

    public static final String FIND_BY_PRODUCT_PERSON_ID = "SELECT id FROM ordering WHERE" +
            " productId = ? and personId = ?";

    public static final String FIND_STATUS_BY_PRODUCT_PERSON_ID = "SELECT status FROM ordering" +
            " WHERE productId = ? and personId = ?";

    public static final String FIND_ALL = "SELECT * FROM ordering";

    public static final String UPDATE = "UPDATE ordering SET productId = ?, personId = ?, status = ? WHERE id = ?";

    public static final String DELETE = "DELETE FROM ordering WHERE id = ?";

    public static final String CHANGE_STATUS_TO_PAID_BY_ID = String.format(
            "UPDATE ordering SET status = '%s' WHERE id = ?", Ordering.Status.PAID.getValue());

    public static final String CHANGE_STATUS_TO_CANCELED_BY_ID = String.format(
            "UPDATE ordering SET status = '%s' WHERE id = ?", Ordering.Status.CANCELED.getValue());

}
