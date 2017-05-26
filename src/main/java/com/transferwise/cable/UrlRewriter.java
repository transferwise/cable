package com.transferwise.cable;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

class UrlRewriter {
    private final Map<String, UrlRewriteRule> rewrites = new LinkedHashMap<>();

    void add(String from, String to) {
        add(from, to, r -> true);
    }

    void add(String from, String to, Predicate<HttpServletRequest> filter) {
        if (rewrites.containsKey(from)) {
            throw new UrlRewriteAlreadyExists(from);
        }

        rewrites.put(from, new UrlRewriteRule(Pattern.compile(from), to, filter));
    }

    String process(String url, HttpServletRequest request) {
        return rewrites.values().stream()
            .reduce(url, (prev, current) -> current.process(prev, request), (url1, url2) -> url1);
    }
}
