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

    public int createAndGetId(Person person) {
        return personDao.createAndGetId(person);
    }

    public Person findById(int id) {
        return personDao.findById(id).orElse(new Person());
    }

    public Person findByLoginAndPassword(String login, char[] password) {
        return personDao.findPersonByLoginAndPassword(login, password).orElse(new Person());
    }

    public List<Person> findAll() {
        return personDao.findAll();
    }

    public List<Person> findOnlyUsers() {
        return personDao.findOnlyUsers();
    }

    public void update(Person person) {
        personDao.update(person);
    }

    public void delete(int id) {
        personDao.delete(id);
    }

    public void blockOrUnblockUserById(int id, String action) {
        if (action.equals("Block")) {
            personDao.blockUserById(id);
        } else if (action.equals("Unblock")) {
            personDao.unblockUserById(id);
        }
    }

    public Person buildStandardUserWithoutId(String login, char[] password, String email) {
        return Person.builder()
                .login(login)
                .password(password)
                .email(email)
                .role(Person.Role.USER)
                .blocked(false)
                .build();
    }
}
