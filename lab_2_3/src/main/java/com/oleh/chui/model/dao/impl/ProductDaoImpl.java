package com.oleh.chui.model.dao.impl;

import com.oleh.chui.model.dao.ProductDao;
import com.oleh.chui.model.dao.impl.query.ProductQueries;
import com.oleh.chui.model.entity.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    @Override
    public void create(Product entity) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.CREATE)) {
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getPrice());
            statement.setString(3, entity.getCategory());
            statement.setDate(4, Date.valueOf(entity.getStartDate()));
            statement.setString(5, entity.getSize().getValue());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during creating Product in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildProductFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding Product in DB by Id", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public List<Product> findAll() {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = buildProductFromResultSet(resultSet);

                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding all Product in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void update(Product entity) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getPrice());
            statement.setString(3, entity.getCategory());
            statement.setDate(4, Date.valueOf(entity.getStartDate()));
            statement.setString(5, entity.getSize().getValue());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during updating Product in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ProductQueries.DELETE)) {
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during deleting Product in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    private Product buildProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product.Builder()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setPrice(resultSet.getBigDecimal("price"))
                .setCategory(resultSet.getString("category"))
                .setStartDate(resultSet.getObject("startDate", LocalDate.class))
                .setSize(Product.Size.valueOf(resultSet.getString("size")))
                .build();
    }
}
