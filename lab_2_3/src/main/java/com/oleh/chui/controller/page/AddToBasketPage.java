package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.util.JspFilePath;
import com.oleh.chui.controller.page.util.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Product;
import com.oleh.chui.model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AddToBasketPage extends PageChainBase {

    private final ProductService productService;

    public AddToBasketPage(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        if (uri.equals(PageURI.CATALOG__SAVE_TO_BASKET) && httpMethod.equals(HttpMethod.POST)) {
            HttpSession session = req.getSession();

            int productId = Integer.parseInt(req.getParameter("id"));

            Product product = productService.findById(productId);

            List<Product> basket = (List<Product>) session.getAttribute("basket");

            basket.add(product);

            resp.sendRedirect(JspFilePath.CATALOG);

        } else {
            processUtiNext(req, resp);
        }
    }
}
