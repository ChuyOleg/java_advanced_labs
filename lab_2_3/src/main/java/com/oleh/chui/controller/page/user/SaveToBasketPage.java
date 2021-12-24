package com.oleh.chui.controller.page.user;

import com.oleh.chui.controller.PageChainBase;
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

public class SaveToBasketPage extends PageChainBase {

    private final ProductService productService;

    public SaveToBasketPage(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = req.getMethod().equals("GET") ? HttpMethod.GET : HttpMethod.valueOf(req.getParameter("method"));

        if (uri.equals(PageURI.CATALOG__SAVE_TO_BASKET) && httpMethod.equals(HttpMethod.POST)) {
            processPostMethod(req, resp);
        } else {
            processUtiNext(req, resp);
        }
    }

    private void processPostMethod(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        int productId = Integer.parseInt(req.getParameter("id"));

        Product product = productService.findById(productId);

        List<Product> basket = (List<Product>) session.getAttribute("basket");

        basket.add(product);

        resp.sendRedirect(PageURI.CATALOG);

    }
}
