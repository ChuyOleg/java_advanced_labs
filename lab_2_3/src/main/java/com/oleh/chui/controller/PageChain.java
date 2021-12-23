package com.oleh.chui.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PageChain {

    PageChain linkWith(PageChain next);

    void processUri(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

}
