package com.hangout.hangout.global.common.domain.entity;

public class Constants {

    public final static String API_PREFIX = "/api/v1";
    public final static String AUTHORIZATION_ENDPOINT = API_PREFIX + "/auth/oauth2/authorize";
    public final static String FAILURE_ENDPOINT = "/login/oauth2/fail";
    public final static String FAILURE_REDIRECT = "http://localhost:8080" + API_PREFIX + "/auth" + FAILURE_ENDPOINT;
    public final static String AUTH_HEADER = "Authorization";
    public final static String AUTH_EXCEPTION = "AuthException";
    public final static String ACCESS_TOKEN_COOKIE_NAME = "accessToken";
    public final static String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";


    public final static String[] PERMIT_ALL_URI_LIST = {
        API_PREFIX+"/auth/**",
        "/v3/api-docs/**",
        "/swagger-ui/**"
    };

    public final static String[] PERMIT_GET_URI_LIST = {
        API_PREFIX+"/post/**",
        API_PREFIX+"/comment/**",
        API_PREFIX+"/report/**"
    };
}
