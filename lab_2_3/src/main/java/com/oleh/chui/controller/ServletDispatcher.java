package com.oleh.chui.controller;

import com.oleh.chui.controller.page.*;
import com.oleh.chui.controller.page.admin.UsersPage;
import com.oleh.chui.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDispatcher extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        PageChain catalogPage = new CatalogPage(ServiceFactory.getInstance().createProductService());
        catalogPage
                .linkWith(new ProcessFilteringPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new ProcessSortingPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new AddToBasketPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new BasketPage())
                .linkWith(new RegistrationPage(ServiceFactory.getInstance().createPersonService()))
                .linkWith(new LoginPage(ServiceFactory.getInstance().createPersonService()))
                .linkWith(new UsersPage(ServiceFactory.getInstance().createPersonService()));

        try {
            catalogPage.processUri(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        // TODO
        // addToBasketPage change get method to post
        // crud product for admin
        // show orderings for user
        // block / unblock users
        // error pages

    }
}
