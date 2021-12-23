package com.oleh.chui.controller.page.user;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.util.JspFilePath;
import com.oleh.chui.controller.page.util.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Ordering;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.entity.Product;
import com.oleh.chui.model.service.OrderingService;
import com.oleh.chui.model.service.ProductService;

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

    public AccountPage(ProductService productService, OrderingService orderingService) {
        this.productService = productService;
        this.orderingService = orderingService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());
        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (uri.equals(PageURI.ACCOUNT) && !role.equals(Person.Role.ADMIN)) {
            if (httpMethod.equals(HttpMethod.GET)) {
                if (role.equals(Person.Role.USER)) {
                    HttpSession session = req.getSession();

                    int personId = (int) session.getAttribute("id");

                    List<Product> productList = productService.findProductListByPersonId(personId);
                    Map<Integer, Ordering.Status> statusMap = orderingService.getStatusMapByPersonIdFromProductList(personId, productList);

                    req.setAttribute("productList", productList);
                    req.setAttribute("statusMap", statusMap);
                    req.getRequestDispatcher(JspFilePath.ACCOUNT).forward(req, resp);
                } else if (role.equals(Person.Role.UNKNOWN)) {
                    resp.sendRedirect(PageURI.LOGIN);
                }

            }
        } else {
            processUtiNext(req, resp);
        }
    }
}
