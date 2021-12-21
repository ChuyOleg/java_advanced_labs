package com.oleh.chui.model.service;

import com.oleh.chui.model.dao.PersonDao;
import com.oleh.chui.model.entity.Person;

import java.util.List;

public class PersonService {

    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void create(Person person) {
        personDao.create(person);
    }

    public Person findById(int id) {
        return personDao.findById(id).orElse(new Person());
    }

    public List<Person> findAll() {
        return personDao.findAll();
    }

    public void update(Person person) {
        personDao.update(person);
    }

    public void delete(int id) {
        personDao.delete(id);
    }

    public void blockUserById(int id) {
        personDao.blockUserById(id);
    }

    public void unblockUserById(int id) {
        personDao.unblockUserById(id);
    }
}