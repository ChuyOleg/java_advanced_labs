package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Product;
import com.oleh.chui.model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ProcessSortingPage extends PageChainBase {

    private final ProductService productService;

    public ProcessSortingPage(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) {

        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        if (uri.equals("/catalog/sort") && httpMethod.equals(HttpMethod.GET)) {
            HttpSession session = req.getSession();

            Object productListObject = session.getAttribute("productList");
            List<Product> productList = productListObject != null ? (List<Product>) productListObject : Collections.emptyList();

            String sortField = req.getParameter("sortField");

            productService.sort(productList, sortField);

            try {
                req.getRequestDispatcher("/catalog.jsp").forward(req, resp);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }

        } else {
            processUtiNext(req, resp);
        }
    }
}
