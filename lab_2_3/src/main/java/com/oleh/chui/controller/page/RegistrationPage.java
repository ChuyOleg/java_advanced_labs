package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.exception.PasswordsNotMatchException;
import com.oleh.chui.controller.exception.PersonAlreadyExistException;
import com.oleh.chui.controller.page.path.JspFilePath;
import com.oleh.chui.controller.page.path.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.controller.validator.RegistrationValidator;
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
        HttpMethod httpMethod = req.getMethod().equals("GET") ? HttpMethod.GET : HttpMethod.valueOf(req.getParameter("method"));
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

    private void processGetMethod(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspFilePath.REGISTRATION).forward(req, resp);
    }

    private void processPostMethod(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();
        char[] passwordCopy = req.getParameter("passwordCopy").toCharArray();
        String email = req.getParameter("email");

        try {
            RegistrationValidator.checkForLoginIsFree(login);
        } catch (PersonAlreadyExistException e) {
            req.setAttribute("email", email);
            req.setAttribute("loginIsNotFreeError", true);
            req.setAttribute("loginIsNotFreeErrorMessage", e.getMessage());
            req.getRequestDispatcher(JspFilePath.REGISTRATION).forward(req, resp);
            return;
        }

        try {
            RegistrationValidator.checkForPasswordsMatching(password, passwordCopy);
        } catch (PasswordsNotMatchException e) {
            req.setAttribute("login", login);
            req.setAttribute("email", email);
            req.setAttribute("passwordsError", true);
            req.setAttribute("passwordsErrorMessage", e.getMessage());
            req.getRequestDispatcher(JspFilePath.REGISTRATION).forward(req, resp);
            return;
        }

        Person person = personService.buildStandardUserWithoutId(login, password, email);

        int personId = personService.createAndGetId(person);

        session.setAttribute("id", personId);
        session.setAttribute("role", person.getRole().getValue());
        resp.sendRedirect(PageURI.CATALOG);
    }

}
