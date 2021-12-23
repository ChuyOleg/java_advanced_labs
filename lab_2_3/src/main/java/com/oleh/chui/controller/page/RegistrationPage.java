package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationPage extends PageChainBase {

    private final PersonService personService;

    public RegistrationPage(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        if (uri.equals("/registration") && httpMethod.equals(HttpMethod.GET)) {
            processGetMethod(req, resp);
        } else if (uri.equals("/registration") && httpMethod.equals(HttpMethod.POST)) {
            processPostMethod(req, resp);
        } else {
            processUtiNext(req, resp);
        }
    }

    public void processGetMethod(HttpServletRequest req,HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    public void processPostMethod(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();
        String email = req.getParameter("email");

        Person person = Person.builder().login(login).password(password).email(email)
                .role(Person.Role.USER).blocked(false).build();

        try {
            try {
                int personId = personService.createAndGetId(person);

                session.setAttribute("id", personId);
                session.setAttribute("role", person.getRole().getValue());
                resp.sendRedirect("/catalog");
            } catch (RuntimeException e) {
                req.setAttribute("error", true);
                req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();

        }
    }

}
