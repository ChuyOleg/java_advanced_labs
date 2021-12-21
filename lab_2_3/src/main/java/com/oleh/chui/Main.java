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
        // DELETE toString for password (Person entity)

        char[] password = {'s', 'u', 'p', 'e', 'r', '_', 'P', 'r', 'o', 't', 'e', 'c', 't', 'i', 'o', 'n'};
        Person.Role role = Person.Role.USER;
        Person person = Person.builder()
                .login("Stephan")
                .password(password)
                .email("HanG@gmail.com")
                .role(Person.Role.USER)
                .blocked(false)
                .build();

        Product product = new Product.Builder().setName("The World of the Wither").setCategory("Books")
                .setPrice(BigDecimal.valueOf(200)).setStartDate(LocalDate.now())
                .setSize(Product.Size.LARGE).build();

        Ordering ordering = new Ordering(1, 1, 1, Ordering.Status.REGISTERED);

        PersonDaoImpl personDao = new PersonDaoImpl();
        ProductDaoImpl productDao = new ProductDaoImpl();
        OrderingDaoImpl orderingDao = new OrderingDaoImpl();

    }

}
