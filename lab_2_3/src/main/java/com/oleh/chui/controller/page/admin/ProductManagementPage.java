package com.oleh.chui.controller.page.admin;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.path.JspFilePath;
import com.oleh.chui.controller.page.path.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Person;
import com.oleh.chui.model.entity.Product;
import com.oleh.chui.model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductManagementPage extends PageChainBase {

    private final ProductService productService;

    public ProductManagementPage(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = req.getMethod().equals("GET") ? HttpMethod.GET : HttpMethod.valueOf(req.getParameter("method"));

        Person.Role role = Person.Role.valueOf(String.valueOf(req.getSession().getAttribute("role")));

        if (uri.equals(PageURI.ADMIN__PRODUCT_MANAGEMENT) && role.equals(Person.Role.ADMIN)) {
            if (httpMethod.equals(HttpMethod.GET)) {
                processGetMethod(req, resp);
            } else if (httpMethod.equals(HttpMethod.POST)) {
                processPostMethod(req, resp);
            } else if (httpMethod.equals(HttpMethod.PUT)) {
                processPutMethod(req, resp);
            } else if (httpMethod.equals(HttpMethod.DELETE)) {
                processDeleteMethod(req, resp);
            }
        } else {
            processUtiNext(req, resp);
        }
    }

    private void processGetMethod(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
    }

    private void processPostMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Product product = buildProductWithoutIdFromHttpBody(req);

        productService.create(product);

        resp.sendRedirect(PageURI.ADMIN__PRODUCT_MANAGEMENT);
    }

    private void processPutMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Product product = buildProductWithoutIdFromHttpBody(req);
        product.setId(Integer.parseInt(req.getParameter("id")));

        productService.update(product);

        resp.sendRedirect(PageURI.CATALOG);
    }

    private void processDeleteMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(req.getParameter("id"));

        productService.delete(productId);

        resp.sendRedirect(PageURI.CATALOG);

    }

    private Product buildProductWithoutIdFromHttpBody(HttpServletRequest req) {
        String name = req.getParameter("name");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
        String category = req.getParameter("category");
        Product.Size size = Product.Size.valueOf(req.getParameter("size"));
        LocalDate date = LocalDate.now();

        return new Product.Builder()
                .setName(name).setPrice(price).setCategory(category)
                .setSize(size).setStartDate(date).build();
    }
}
