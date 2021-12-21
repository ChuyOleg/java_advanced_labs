package com.oleh.chui.model.dao.impl.query;

public class PersonQueries {

    public static final String CREATE = "INSERT INTO person(login, password, email, role, blocked)" +
            " values(?, ?, ?, ?, ?)";

    public static final String FIND_BY_ID = "SELECT * FROM person WHERE id = ?";

    public static final String FIND_ALL = "SELECT * FROM person";

    public static final String UPDATE = "UPDATE person SET login = ?, password = ?, email = ?," +
            " role = ?, blocked = ? WHERE id = ?";

    public static final String DELETE = "DELETE FROM person WHERE id = ?";

}
