package com.oleh.chui.model.dao;

import com.oleh.chui.model.entity.Person;

public interface PersonDao extends GenericDao<Person> {

    void blockUserById(int id);

    void unblockUserById(int id);

}
