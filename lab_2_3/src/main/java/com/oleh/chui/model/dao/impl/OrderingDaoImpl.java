package com.oleh.chui.model.dao.impl;

import com.oleh.chui.model.dao.OrderingDao;
import com.oleh.chui.model.dao.impl.query.OrderingQueries;
import com.oleh.chui.model.entity.Ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderingDaoImpl implements OrderingDao {
    @Override
    public void create(Ordering entity) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.CREATE)) {
            statement.setInt(1, entity.getProductId());
            statement.setInt(2, entity.getPersonId());
            statement.setString(3, entity.getStatus().getValue());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during creating Ordering in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public Optional<Ordering> findById(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Ordering.builder()
                        .id(resultSet.getInt("id"))
                        .productId(resultSet.getInt("productId"))
                        .personId(resultSet.getInt("personId"))
                        .status(Ordering.Status.valueOf(resultSet.getString("status")))
                        .build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding Ordering in DB by Id", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public List<Ordering> findAll() {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Ordering> orderingList = new ArrayList<>();
            while (resultSet.next()) {
                Ordering ordering = Ordering.builder()
                        .id(resultSet.getInt("id"))
                        .productId(resultSet.getInt("productId"))
                        .personId(resultSet.getInt("personId"))
                        .status(Ordering.Status.valueOf(resultSet.getString("status")))
                        .build();

                orderingList.add(ordering);
            }
            return orderingList;
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding all Ordering in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void update(Ordering entity) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.UPDATE)) {
            statement.setInt(1, entity.getProductId());
            statement.setInt(2, entity.getPersonId());
            statement.setString(3, entity.getStatus().getValue());
            statement.setInt(4, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during updating Ordering in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.DELETE)) {
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during deleting Ordering in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }
}
