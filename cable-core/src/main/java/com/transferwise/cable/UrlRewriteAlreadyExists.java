package com.transferwise.cable;

public class UrlRewriteAlreadyExists extends RuntimeException {
    UrlRewriteAlreadyExists(String from) {
        super("A rewrite from \"" + from + "\" already exists");
    }
}
