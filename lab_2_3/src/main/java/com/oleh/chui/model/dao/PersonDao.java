package com.oleh.chui.model.dao;

import com.oleh.chui.model.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao extends GenericDao<Person> {

    Optional<Person> findPersonByLoginAndPassword(String login, char[] password);

    List<Person> findOnlyUsers();

    int createAndGetId(Person person);

    void blockUserById(int id);

    void unblockUserById(int id);

}
