package com.oleh.chui.controller.page.user;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.path.JspFilePath;
import com.oleh.chui.controller.page.path.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Ordering;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.service.OrderingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class BasketPage extends PageChainBase {

    private final OrderingService orderingService;

    public BasketPage(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = req.getMethod().equals("GET") ? HttpMethod.GET : HttpMethod.valueOf(req.getParameter("method"));
        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (uri.equals(PageURI.BASKET) && !role.equals(Person.Role.ADMIN)) {
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
        req.getRequestDispatcher(JspFilePath.USER__BASKET).forward(req, resp);
    }

    private void processPostMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (role.equals(Person.Role.UNKNOWN)) {
            resp.sendRedirect(PageURI.LOGIN);
        } else if (role.equals(Person.Role.USER)) {
            HttpSession session = req.getSession();

            int productId = Integer.parseInt(req.getParameter("id"));
            int personId = (int) session.getAttribute("id");

            Ordering ordering = orderingService.buildStandardOrderingWithoutId(productId, personId);

            orderingService.create(ordering);

            resp.sendRedirect(PageURI.BASKET);
        }
    }
}
