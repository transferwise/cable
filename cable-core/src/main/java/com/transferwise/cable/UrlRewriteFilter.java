package com.transferwise.cable;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Predicate;

public class UrlRewriteFilter implements Filter {
    private final UrlRewriter urlRewrite = new UrlRewriter();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public UrlRewriteFilter rewrite(String from, String to) {
        urlRewrite.add(from, to);

        return this;
    }

    public UrlRewriteFilter rewrite(String from, String to, Predicate<HttpServletRequest> filter) {
        urlRewrite.add(from, to, filter);

        return this;
    }

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        chain.doFilter(request, new UrlRewriteWrappedResponse(
            (HttpServletResponse) response,
            (HttpServletRequest) request,
            urlRewrite)
        );
    }

    @Override
    public void destroy() {
    }
}
