package com.oleh.chui.controller;

import com.oleh.chui.controller.page.*;
import com.oleh.chui.controller.page.admin.OrdersManagement;
import com.oleh.chui.controller.page.admin.ProductManagementPage;
import com.oleh.chui.controller.page.admin.UsersManagementPage;
import com.oleh.chui.controller.page.user.AccountPage;
import com.oleh.chui.controller.page.user.BasketPage;
import com.oleh.chui.controller.page.user.SaveToBasketPage;
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
                .linkWith(new ProcessSortingPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new SaveToBasketPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new BasketPage(ServiceFactory.getInstance().createOrderingService()))
                .linkWith(new RegistrationPage(ServiceFactory.getInstance().createPersonService()))
                .linkWith(new LoginPage(ServiceFactory.getInstance().createPersonService()))
                .linkWith(new UsersManagementPage(ServiceFactory.getInstance().createPersonService()))
                .linkWith(new ProductManagementPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new LogoutPage())
                .linkWith(new AccountPage(ServiceFactory.getInstance().createProductService(),
                        ServiceFactory.getInstance().createOrderingService(),
                        ServiceFactory.getInstance().createPersonService()))
                .linkWith(new OrdersManagement(ServiceFactory.getInstance().createProductService(),
                        ServiceFactory.getInstance().createOrderingService(),
                        ServiceFactory.getInstance().createPersonService()));

        try {
            catalogPage.processUri(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        // TODO
        // change everywhere way of getting httpMethod (post => post | put | delete)
        // think about bag when open login page don't using logout button
        // add validation for every post/update form
        // make ordered product with 'ordered' label in basket
        // clear ALL Session when exit from account (products in basket!!!!!) ?????
        // think about blocked users
        // add logger
        // add validation of data
        // update product
        // show orderings for user
        // error pages

    }
}
