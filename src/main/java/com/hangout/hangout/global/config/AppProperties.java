package com.hangout.hangout.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration")
@PropertySource(value = {"classpath:/application-oauth.yml"})
@Setter
@Getter
public class AppProperties {
    private GoogleRegistration google;

    @Setter
    @Getter
    public static class GoogleRegistration {
        private String client_id;
        private String redirect_uri;
    }
}
