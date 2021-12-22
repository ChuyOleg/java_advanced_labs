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
import java.util.List;
import java.util.Map;

public class ProcessFilteringPage extends PageChainBase {

    private final ProductService productService;

    public ProcessFilteringPage(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) {

        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        if (uri.equals("/catalog/filter") && httpMethod.equals(HttpMethod.GET)) {
            HttpSession session = req.getSession();

            List<Product> productList = productService.findAll();

            Map<String, String[]> filterMap = req.getParameterMap();

            List<Product> filteredProductList = productService.filter(productList, filterMap);

            session.setAttribute("productList", filteredProductList);

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
