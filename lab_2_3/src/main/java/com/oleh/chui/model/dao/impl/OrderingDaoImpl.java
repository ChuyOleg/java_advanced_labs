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
                return Optional.of(buildOrderingFromResultSet(resultSet));
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
                Ordering ordering = buildOrderingFromResultSet(resultSet);

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

    @Override
    public void changeStatusToPaidById(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.CHANGE_STATUS_TO_PAID_BY_ID)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during changing ordering status to Paid", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void changeStatusToCanceledById(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.CHANGE_STATUS_TO_CANCELED_BY_ID)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during changing ordering status to Canceled", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public List<Ordering> findAllByPersonId(int personId) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.FIND_ALL_BY_PERSON_ID)) {
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();
            List<Ordering> orderingList = new ArrayList<>();
            while (resultSet.next()) {
                Ordering ordering = buildOrderingFromResultSet(resultSet);

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
    public Ordering.Status findStatusByProductIdAndPersonId(int productId, int personId) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.FIND_STATUS_BY_PRODUCT_PERSON_ID)) {
            statement.setInt(1, productId);
            statement.setInt(2, personId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Ordering.Status.valueOf(resultSet.getString("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding status in DB by product and person id`s", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
        throw new RuntimeException(String.format("Ordering isn`t exist with" +
                " productId = %s and personId = %s", productId, personId));
    }

    @Override
    public boolean isExistByProductIdAndPersonId(int productId, int personId) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(OrderingQueries.FIND_BY_PRODUCT_PERSON_ID)) {
            statement.setInt(1, productId);
            statement.setInt(2, personId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding status in DB by product and person id`s", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    private Ordering buildOrderingFromResultSet(ResultSet resultSet) throws SQLException {
        return Ordering.builder()
                .id(resultSet.getInt("id"))
                .productId(resultSet.getInt("productId"))
                .personId(resultSet.getInt("personId"))
                .status(Ordering.Status.valueOf(resultSet.getString("status")))
                .build();
    }
}
