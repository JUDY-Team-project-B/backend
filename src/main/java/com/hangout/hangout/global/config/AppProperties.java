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

    @Getter
    @Setter
    public static class Registration {

        private String redirect_uri;
        private String redirect_client;
    }

    @Getter
    @Setter
    public static class GoogleRegistration extends Registration {

        private String scope;
    }
}