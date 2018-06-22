package com.transferwise.cable;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "cable", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(CableAutoConfiguration.UrlRewriteProperties.class)
public class CableAutoConfiguration {
    @ConfigurationProperties(prefix = "cable")
    public static class UrlRewriteProperties {
        private List<Rewrite> rewrites = new ArrayList<>();

        public void setRewrites(List<Rewrite> rewrites) {
            this.rewrites = rewrites;
        }

        public List<Rewrite> getRewrites() {
            return rewrites;
        }

        public static class Rewrite {
            private String from;
            private String to;

            public void setFrom(String from) {
                this.from = from;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getFrom() {
                return from;
            }

            public String getTo() {
                return to;
            }
        }
    }

    @Bean
    public FilterRegistrationBean urlRewriteFilter(UrlRewriteProperties properties) {
        UrlRewriteFilter filter = new UrlRewriteFilter();

        properties.getRewrites().forEach(rewrite -> filter.rewrite(rewrite.getFrom(), rewrite.getTo()));

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(15);
        return registrationBean;
    }
}
