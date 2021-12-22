package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.util.HttpMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BasketPage extends PageChainBase {

    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        if (uri.equals("/basket") && httpMethod.equals(HttpMethod.GET)) {
            try {
                req.getRequestDispatcher("/basket.jsp").forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }

        } else {
            processUtiNext(req, resp);
        }
    }
}
