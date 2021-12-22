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

public class LoginPage extends PageChainBase {

    private final PersonService personService;

    public LoginPage(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        if (uri.equals("/login") && httpMethod.equals(HttpMethod.GET)) {
            processGetMethod(req, resp);
        } else if (uri.equals("/login") && httpMethod.equals(HttpMethod.POST)) {
            processPostMethod(req, resp);
        } else {
            processUtiNext(req, resp);
        }
    }

    public void processGetMethod(HttpServletRequest req,HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    public void processPostMethod(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();

        Person person = personService.findByLoginAndPassword(login, password);

        try {
            if (person.getId() != 0) {
                session.setAttribute("id", person.getId());
                session.setAttribute("role", person.getRole());
                resp.sendRedirect("/catalog");
            } else {
                req.setAttribute("error", true);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
