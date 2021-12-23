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

        if (uri.equals(PageURI.LOGIN) && httpMethod.equals(HttpMethod.GET)) {
            processGetMethod(req, resp);
        } else if (uri.equals(PageURI.LOGIN) && httpMethod.equals(HttpMethod.POST)) {
            processPostMethod(req, resp);
        } else {
            processUtiNext(req, resp);
        }
    }

    public void processGetMethod(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspFilePath.LOGIN).forward(req, resp);
    }

    public void processPostMethod(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();

        Person person = personService.findByLoginAndPassword(login, password);

        if (person.getId() != 0) {
            session.setAttribute("id", person.getId());
            session.setAttribute("role", person.getRole().getValue());
            resp.sendRedirect(JspFilePath.CATALOG);
        } else {
            req.setAttribute("error", true);
            req.getRequestDispatcher(JspFilePath.LOGIN).forward(req, resp);
        }
    }
}
