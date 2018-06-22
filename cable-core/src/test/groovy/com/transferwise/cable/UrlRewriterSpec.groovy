package com.transferwise.cable

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class UrlRewriterSpec extends Specification {
    def request
    def rewriter

    def setup() {
        request = Mock(HttpServletRequest)
        rewriter = new UrlRewriter()
    }

    def 'it should process to the same url when no rewrites are defined'() {
        when: def processed = process('http://example.com')

        then: processed == 'http://example.com'
    }

    private process(url) {
        rewriter.process(url, request)
    }

    def 'it should rewrite urls'() {
        given: rewriter.add(from, to)

        when: def processed = process(url)

        then: processed == expected

        where:
        url                  | from                 | to            || expected
        'http://example.com' | 'example.com'        | 'cdn.com'     || 'http://cdn.com'
        '/es/page'           | '^/([a-z]{2})/(.*)$' | '/$1/blog/$2' || '/es/blog/page'
        'relative/path'      | '/path'              | '/path2'      || 'relative/path2'
    }

    def 'it should chain rewrites'() {
        given:
            rewriter.add('/es/', '/us/')
            rewriter.add('/us/', '/gb/')
            rewriter.add('/gb/', '/nz/')

        when: def processed = process('/es/')

        then: processed == '/nz/'
    }

    def 'it should filter rewrites'() {
        given: rewriter.add('/es/', '/us/', { it -> false })

        when: def processed = process('/es/')

        then: processed == '/es/'
    }

    def 'it should accept same route twice'() {
        when:
            rewriter.add('/es/', '/us/')
            rewriter.add('/es/', '/gb/')

        then:
            thrown RuntimeException
    }
}
