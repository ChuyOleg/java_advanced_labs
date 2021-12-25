package com.oleh.chui.controller.page.admin;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.exception.NonExistentSizeException;
import com.oleh.chui.controller.exception.PriceIsNegativeNumberException;
import com.oleh.chui.controller.exception.PriceIsNotNumberException;
import com.oleh.chui.controller.page.path.JspFilePath;
import com.oleh.chui.controller.page.path.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.controller.validator.PriceValidator;
import com.oleh.chui.controller.validator.SizeValidator;
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
        req.setAttribute("id", req.getParameter("id"));
        req.setAttribute("name", req.getParameter("name"));
        req.setAttribute("price", req.getParameter("price"));
        req.setAttribute("category", req.getParameter("category"));
        req.setAttribute("size", req.getParameter("size"));

        req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
    }

    private void processPostMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        Product product;
        try {
            product = buildProductWithoutIdFromHttpBody(req);
        } catch (PriceIsNotNumberException e) {
            req.setAttribute("priceIsNotNumberError", true);
            req.setAttribute("priceIsNotNumberErrorMessage", e.getMessage());
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("category", req.getParameter("category"));
            req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
            return;
        } catch (NonExistentSizeException e) {
            req.setAttribute("nonExistentSizeError", true);
            req.setAttribute("nonExistentSizeErrorMessage", e.getMessage());
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("category", req.getParameter("category"));
            req.setAttribute("price", req.getParameter("price"));
            req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
            return;
        } catch (PriceIsNegativeNumberException e) {
            req.setAttribute("priceIsNegativeError", true);
            req.setAttribute("priceIsNegativeErrorMessage", e.getMessage());
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("category", req.getParameter("category"));
            req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
            return;
        }

        productService.create(product);

        resp.sendRedirect(PageURI.ADMIN__PRODUCT_MANAGEMENT);
    }

    private void processPutMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        Product product;
        try {
            product = buildProductWithoutIdFromHttpBody(req);
        } catch (PriceIsNotNumberException e) {
            req.setAttribute("priceIsNotNumberError", true);
            req.setAttribute("priceIsNotNumberErrorMessage", e.getMessage());
            req.setAttribute("id", req.getParameter("id"));
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("category", req.getParameter("category"));
            req.setAttribute("size", req.getParameter("size"));
            req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
            return;
        } catch (NonExistentSizeException e) {
            req.setAttribute("nonExistentSizeError", true);
            req.setAttribute("nonExistentSizeErrorMessage", e.getMessage());
            req.setAttribute("id", req.getParameter("id"));
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("category", req.getParameter("category"));
            req.setAttribute("price", req.getParameter("price"));
            req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
            return;
        } catch (PriceIsNegativeNumberException e) {
            req.setAttribute("priceIsNegativeError", true);
            req.setAttribute("priceIsNegativeErrorMessage", e.getMessage());
            req.setAttribute("id", req.getParameter("id"));
            req.setAttribute("name", req.getParameter("name"));
            req.setAttribute("category", req.getParameter("category"));
            req.setAttribute("size", req.getParameter("size"));
            req.getRequestDispatcher(JspFilePath.ADMIN__CREATE_PRODUCT).forward(req, resp);
            return;
        }

        product.setId(Integer.parseInt(req.getParameter("id")));

        productService.update(product);

        resp.sendRedirect(PageURI.CATALOG);
    }

    private void processDeleteMethod(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        int productId = Integer.parseInt(req.getParameter("id"));

        productService.delete(productId);

        resp.sendRedirect(PageURI.CATALOG);

    }

    private Product buildProductWithoutIdFromHttpBody(HttpServletRequest req)
            throws PriceIsNotNumberException, NonExistentSizeException, PriceIsNegativeNumberException {

        String name = req.getParameter("name");

        String priceString = req.getParameter("price");
        PriceValidator.checkForCorrectPrice(priceString);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceString));

        String category = req.getParameter("category");

        String sizeString = req.getParameter("size");
        SizeValidator.checkForCorrectSize(sizeString);
        Product.Size size = Product.Size.valueOf(sizeString);

        LocalDate date = LocalDate.now();

        return new Product.Builder()
                .setName(name).setPrice(price).setCategory(category)
                .setSize(size).setStartDate(date).build();
    }
}
