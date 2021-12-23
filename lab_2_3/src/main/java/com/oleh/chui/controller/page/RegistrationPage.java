package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.util.JspFilePath;
import com.oleh.chui.controller.page.util.PageURI;
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
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());
        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (uri.equals(PageURI.REGISTRATION) && role.equals(Person.Role.UNKNOWN)) {
            if (httpMethod.equals(HttpMethod.GET)) {
                processGetMethod(req, resp);
            } else if (httpMethod.equals(HttpMethod.POST)) {
                processPostMethod(req, resp);
            }
        } else {
            processUtiNext(req, resp);
        }
    }

    public void processGetMethod(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspFilePath.REGISTRATION).forward(req, resp);
    }

    public void processPostMethod(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();
        String email = req.getParameter("email");

        Person person = Person.builder().login(login).password(password).email(email)
                .role(Person.Role.USER).blocked(false).build();

        try {
            int personId = personService.createAndGetId(person);

            session.setAttribute("id", personId);
            session.setAttribute("role", person.getRole().getValue());
            resp.sendRedirect(PageURI.CATALOG);
        } catch (RuntimeException e) {
            req.setAttribute("error", true);
            req.getRequestDispatcher(JspFilePath.REGISTRATION).forward(req, resp);
        }
    }

}
