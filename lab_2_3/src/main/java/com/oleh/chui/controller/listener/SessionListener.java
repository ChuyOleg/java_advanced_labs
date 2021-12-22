package com.oleh.chui.controller.listener;

import com.oleh.chui.model.entity.Product;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        final HttpSession httpSession = se.getSession();

        List<Product> basket = new ArrayList<>();

        httpSession.setAttribute("basket", basket);
    }

}
