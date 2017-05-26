package com.transferwise.cable;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UrlRewriteRule {
    private final Pattern from;
    private final String to;
    private final Predicate<HttpServletRequest> filter;

    UrlRewriteRule(Pattern from, String to, Predicate<HttpServletRequest> filter) {
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    String process(String url, HttpServletRequest request) {
        Matcher m = from.matcher(url);
        if (m.find() && filter.test(request)) {
            return m.replaceAll(to);
        }

        return url;
    }
}
