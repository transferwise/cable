package com.transferwise.cable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class UrlRewriteWrappedResponse extends HttpServletResponseWrapper {
    private final HttpServletRequest request;
    private final UrlRewriter urlRewrite;

    UrlRewriteWrappedResponse(
        HttpServletResponse response,
        HttpServletRequest request,
        UrlRewriter urlRewrite
    ) {
        super(response);
        this.request = request;
        this.urlRewrite = urlRewrite;
    }

    @Override
    public String encodeUrl(String url) {
        return urlRewrite.process(url, request);
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return urlRewrite.process(url, request);
    }

    @Override
    public String encodeURL(String url) {
        return urlRewrite.process(url, request);
    }

    @Override
    public String encodeRedirectURL(String url) {
        return urlRewrite.process(url, request);
    }
}
