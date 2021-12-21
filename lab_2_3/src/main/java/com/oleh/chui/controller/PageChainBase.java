package com.oleh.chui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class PageChainBase implements PageChain {

    private PageChain next;

    @Override
    public PageChain linkWith(PageChain next) {
        this.next = next;
        return next;
    }

    protected void processUtiNext(HttpServletRequest req, HttpServletResponse resp) {
        if (next != null) {
            next.processUri(req, resp);
        }
    }
}
