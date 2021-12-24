package com.oleh.chui.controller.page.user;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.path.JspFilePath;
import com.oleh.chui.controller.page.path.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Ordering;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.entity.Product;
import com.oleh.chui.model.service.OrderingService;
import com.oleh.chui.model.service.PersonService;
import com.oleh.chui.model.service.ProductService;
import com.oleh.chui.model.service.util.UtilService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AccountPage extends PageChainBase {

    private final ProductService productService;
    private final OrderingService orderingService;
    private final PersonService personService;

    public AccountPage(ProductService productService, OrderingService orderingService, PersonService personService) {
        this.productService = productService;
        this.orderingService = orderingService;
        this.personService = personService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = req.getMethod().equals("GET") ? HttpMethod.GET : HttpMethod.valueOf(req.getParameter("method"));
        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (uri.equals(PageURI.ACCOUNT) && !role.equals(Person.Role.ADMIN)) {
            if (httpMethod.equals(HttpMethod.GET)) {
                processGetMethod(req, resp);
            }
        } else {
            processUtiNext(req, resp);
        }
    }

    private void processGetMethod(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (role.equals(Person.Role.USER)) {
            HttpSession session = req.getSession();

            int personId = (int) session.getAttribute("id");

            List<Ordering> orderingList = orderingService.findAllByPersonId(personId);
            List<Product> productList = productService.findAllByPersonId(personId);
            Person person = personService.findById(personId);

            Map<Integer, Product> productMapByOrderingId = UtilService.getProductIMapByOrderingId(productList, orderingList);

            req.setAttribute("orderingList", orderingList);
            req.setAttribute("person", person);
            req.setAttribute("productMapByOrderingId", productMapByOrderingId);
            req.getRequestDispatcher(JspFilePath.USER__ACCOUNT).forward(req, resp);
        } else if (role.equals(Person.Role.UNKNOWN)) {
            resp.sendRedirect(PageURI.LOGIN);
        }
    }
}
