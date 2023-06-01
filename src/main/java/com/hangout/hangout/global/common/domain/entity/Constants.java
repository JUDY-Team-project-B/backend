package com.hangout.hangout.global.common.domain.entity;

public class Constants {

    public final static String API_PREFIX = "/api/v1";
    public final static String AUTHORIZATION_ENDPOINT = API_PREFIX + "/auth/oauth2/authorize";
    public final static String FAILURE_ENDPOINT = "/login/fail";
    public final static String FAILURE_REDIRECT = "http://localhost:8080" + API_PREFIX + "/auth" + FAILURE_ENDPOINT;
    public final static String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";

    public final static String[] SWAGGER_URI_LIST = {
        "/api/v1/auth/**","/",
        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**",
        "/h2-console/**",
        "/favicon.ico"
    };
}
