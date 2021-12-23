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

public class LoginPage extends PageChainBase {

    private final PersonService personService;

    public LoginPage(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());
        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (uri.equals(PageURI.LOGIN) && role.equals(Person.Role.UNKNOWN)) {
            if (httpMethod.equals(HttpMethod.GET)) {
                processGetMethod(req, resp);
            } else if (httpMethod.equals(HttpMethod.POST)) {
                processPostMethod(req, resp);
            }
        } else {
            processUtiNext(req, resp);
        }
    }

    private void processGetMethod(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspFilePath.LOGIN).forward(req, resp);
    }

    private void processPostMethod(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();

        Person person = personService.findByLoginAndPassword(login, password);

        if (person.getId() != 0) {
            if (!person.getBlocked()) {
                session.setAttribute("id", person.getId());
                session.setAttribute("role", person.getRole().getValue());
                resp.sendRedirect(PageURI.CATALOG);
            } else {
                req.setAttribute("errorBlocked", true);
                req.getRequestDispatcher(JspFilePath.LOGIN).forward(req, resp);
            }
        } else {
            req.setAttribute("error", true);
            req.getRequestDispatcher(JspFilePath.LOGIN).forward(req, resp);
        }
    }
}
