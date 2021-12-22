package com.oleh.chui.controller;

import com.oleh.chui.controller.page.*;
import com.oleh.chui.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDispatcher extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PageChain catalogPage = new CatalogPage(ServiceFactory.getInstance().createProductService());
        catalogPage
                .linkWith(new ProcessFilteringPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new ProcessSortingPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new AddToBasketPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new BasketPage())
                .linkWith(new LoginPage(ServiceFactory.getInstance().createPersonService()));

        catalogPage.processUri(req, resp);

    }
}
