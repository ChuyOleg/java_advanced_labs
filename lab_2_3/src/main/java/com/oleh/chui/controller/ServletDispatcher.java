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

    // CatalogPage is defaultPage so CatalogPage must be final in chaining !!!
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        PageChain loginPage = new LoginPage(ServiceFactory.getInstance().createPersonService());
        loginPage
                .linkWith(new ProcessSortingPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new SaveToBasketPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new BasketPage(ServiceFactory.getInstance().createOrderingService()))
                .linkWith(new RegistrationPage(ServiceFactory.getInstance().createPersonService()))
                .linkWith(new UsersManagementPage(ServiceFactory.getInstance().createPersonService()))
                .linkWith(new ProductManagementPage(ServiceFactory.getInstance().createProductService()))
                .linkWith(new LogoutPage())
                .linkWith(new AccountPage(ServiceFactory.getInstance().createProductService(),
                        ServiceFactory.getInstance().createOrderingService(),
                        ServiceFactory.getInstance().createPersonService()))
                .linkWith(new OrdersManagement(ServiceFactory.getInstance().createProductService(),
                        ServiceFactory.getInstance().createOrderingService(),
                        ServiceFactory.getInstance().createPersonService()))
                .linkWith(new CatalogPage(ServiceFactory.getInstance().createProductService()));


        try {
            loginPage.processUri(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        // TODO
        // add validation for every post/update form
        // add logger
        // error pages

    }
}
