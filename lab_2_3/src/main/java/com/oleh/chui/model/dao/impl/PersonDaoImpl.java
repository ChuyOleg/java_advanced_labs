package com.oleh.chui.model.dao.impl;

import com.oleh.chui.model.dao.PersonDao;
import com.oleh.chui.model.dao.impl.query.PersonQueries;
import com.oleh.chui.model.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao {
    @Override
    public void create(Person entity) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.CREATE)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, String.valueOf(entity.getPassword()));
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getRole().getValue());
            statement.setBoolean(5, entity.getBlocked());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during creation Person in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }

    }

    @Override
    public Optional<Person> findById(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               return Optional.of(buildPersonFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding Person in DB by id", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public List<Person> findAll() {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Person> personList = new ArrayList<>();
            while (resultSet.next()) {
                Person person = buildPersonFromResultSet(resultSet);

                personList.add(person);
            }
            return personList;
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding All Person in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void update(Person entity) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.UPDATE)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, String.valueOf(entity.getPassword()));
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getRole().getValue());
            statement.setBoolean(5, entity.getBlocked());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during updating Person in DB", e);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.DELETE)) {
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during deleting Person in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }


    @Override
    public Optional<Person> findPersonByLoginAndPassword(String login, char[] password) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.FIND_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, String.valueOf(password));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildPersonFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding Person in DB by id", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public List<Person> findOnlyUsers() {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.FIND_ONLY_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Person> personList = new ArrayList<>();
            while (resultSet.next()) {
                Person person = buildPersonFromResultSet(resultSet);

                personList.add(person);
            }
            return personList;
        } catch (SQLException e) {
            throw new RuntimeException("Exception during finding Only Users in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public int createAndGetId(Person person) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(
                PersonQueries.CREATE_AND_GET_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getLogin());
            statement.setString(2, String.valueOf(person.getPassword()));
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getRole().getValue());
            statement.setBoolean(5, person.getBlocked());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Exception during creation Person in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
        throw new RuntimeException("Exception during creating new Person and getting id");
    }

    @Override
    public boolean loginIsNotFree(char[] login) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.LOGIN_IS_FREE)) {
            statement.setString(1, String.valueOf(login));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during checking is login free in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void blockUserById(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.BLOCK_USER_BY_ID)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during blocking User in DB", e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    @Override
    public void unblockUserById(int id) {
        Connection connection = ConnectionPoolHolder.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(PersonQueries.UNBLOCK_USER_BY_ID)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Exception during unblocking User in DB" , e);
        } finally {
            ConnectionPoolHolder.closeConnection(connection);
        }
    }

    private Person buildPersonFromResultSet(ResultSet resultSet) throws SQLException {
        return Person.builder()
                .id(resultSet.getInt("id"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password").toCharArray())
                .email(resultSet.getString("email"))
                .role(Person.Role.valueOf(resultSet.getString("role")))
                .blocked(resultSet.getBoolean("blocked"))
                .build();
    }
}
