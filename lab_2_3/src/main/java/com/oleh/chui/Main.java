package com.oleh.chui;

import com.oleh.chui.model.dao.PersonDao;
import com.oleh.chui.model.dao.ProductDao;
import com.oleh.chui.model.dao.impl.OrderingDaoImpl;
import com.oleh.chui.model.dao.impl.PersonDaoImpl;
import com.oleh.chui.model.dao.impl.ProductDaoImpl;
import com.oleh.chui.model.entity.Ordering;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        // TODO
        // CHECK COPY (same login) DURING CREATING PERSON
        // ADD UNIQUE CONSTRAINT FOR FIELD LOGIN IN DB

        char[] password = {'s', 'u', 'p', 'e', 'r', '_', 'P', 'r', 'o', 't', 'e', 'c', 't', 'i', 'o', 'n'};
        Person.Role role = Person.Role.USER;
        Person person = Person.builder()
                .login("Giga")
                .password(password)
                .email("HanG@gmail.com")
                .role(Person.Role.USER)
                .blocked(false)
                .build();

        Product product = new Product.Builder().setId(2).setName("Cow").setCategory("Animal")
                .setPrice(BigDecimal.valueOf(1100)).setStartDate(LocalDate.now())
                .setSize(Product.Size.SMALL).build();

        Ordering ordering = new Ordering(1, 1, 1, Ordering.Status.PAID);

        PersonDaoImpl personDao = new PersonDaoImpl();
        ProductDaoImpl productDao = new ProductDaoImpl();
        OrderingDaoImpl orderingDao = new OrderingDaoImpl();

//        orderingDao.create(ordering);
//        orderingDao.update(ordering);

        Optional<Ordering> orderingFromDB = orderingDao.findById(1);


        orderingFromDB.ifPresent(System.out::println);

    }

}
