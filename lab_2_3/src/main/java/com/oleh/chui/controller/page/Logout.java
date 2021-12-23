package com.oleh.chui.controller.page;

import com.oleh.chui.controller.PageChainBase;
import com.oleh.chui.controller.page.util.PageURI;
import com.oleh.chui.controller.util.HttpMethod;
import com.oleh.chui.model.entity.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logout extends PageChainBase {
    @Override
    public void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        HttpMethod httpMethod = HttpMethod.valueOf(req.getMethod());

        if (uri.equals(PageURI.LOGOUT) && httpMethod.equals(HttpMethod.GET)) {
            HttpSession session = req.getSession();

            session.setAttribute("id", null);
            session.setAttribute("role", Person.Role.UNKNOWN);

            resp.sendRedirect(PageURI.LOGIN);
        } else {
            processUtiNext(req, resp);
        }
    }
}
