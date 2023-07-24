package com.hangout.hangout.global.common.domain.entity;

public class Constants {

    public final static String API_PREFIX = "/api/v1";
    public final static String AUTHORIZATION_ENDPOINT = API_PREFIX + "/auth/oauth2/authorize";
    public final static String FAILURE_ENDPOINT = "/login/oauth2/fail";
    public final static String FAILURE_REDIRECT = "http://localhost:8080" + API_PREFIX + "/auth" + FAILURE_ENDPOINT;

    public final static String[] SWAGGER_URI_LIST = {
        "/v3/api-docs/**",
        "/swagger-ui/**"
    };

    public final static String[] PERMIT_ALL_URI_LIST = {
        API_PREFIX+"/auth/**",
        API_PREFIX+"/post/**",
        API_PREFIX+"/comment/**",
        API_PREFIX+"/report/**"
    };
}
