package com.oleh.chui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PageChain {

    PageChain linkWith(PageChain next);

    void processUri(HttpServletRequest req, HttpServletResponse resp);

}
