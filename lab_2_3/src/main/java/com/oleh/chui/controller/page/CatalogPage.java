package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.path.JspFilePath;
import com.oleh.chui.controller.page.path.PageURI;
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
import java.util.Set;

public class CatalogPage extends PageChainBase {

    private final ProductService productService;

    public CatalogPage(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = req.getMethod().equals("GET") ? HttpMethod.GET : HttpMethod.valueOf(req.getParameter("method"));

        if (!uri.equals(PageURI.FAVICON_ICO) && httpMethod.equals(HttpMethod.GET)) {
            processGetMethod(req, resp);
        } else {
            processUtiNext(req, resp);
        }
    }

    private void processGetMethod(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Product> productList = productService.findAll();
        Set<String> categorySet = productService.getCategorySet(productList);

        Map<String, String[]> filterMap = req.getParameterMap();
        List<Product> filteredProductList = productService.filter(productList, filterMap);

        session.setAttribute("productList", filteredProductList);
        req.setAttribute("categorySet", categorySet);
        req.getRequestDispatcher(JspFilePath.CATALOG).forward(req, resp);
    }
}
