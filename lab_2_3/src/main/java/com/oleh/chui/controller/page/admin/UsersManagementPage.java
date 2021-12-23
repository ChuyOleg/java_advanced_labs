package com.oleh.chui.controller.page.admin;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.util.JspFilePath;
import com.oleh.chui.controller.page.util.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersManagementPage extends PageChainBase {

    private final PersonService personService;

    public UsersManagementPage(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (uri.equals(PageURI.ADMIN__USERS) && role.equals(Person.Role.ADMIN)) {
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
        List<Person> personList = personService.findOnlyUsers();

        req.setAttribute("personList", personList);

        req.getRequestDispatcher(JspFilePath.ADMIN__USERS).forward(req, resp);
    }

    private void processPostMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        int personId = Integer.parseInt(req.getParameter("id"));
        String blockOrUnblock = req.getParameter("action");

        personService.blockOrUnblockUserById(personId, blockOrUnblock);

        resp.sendRedirect(PageURI.ADMIN__USERS);
    }
}
