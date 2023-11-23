package com.hangout.hangout.global.common.domain.repository;

import com.hangout.hangout.global.util.CookieUtil;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

/**
 * 최초 인가 시 요청된 redirect_url을 저장하는 class
 */
@Component
@RequiredArgsConstructor
public class CookieAuthorizationRequestRepository implements AuthorizationRequestRepository {

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect-uri";
    public static final int COOKIE_EXPIRE_SECONDS = 180;

    /**
     * 처음 social login uri로 요청 시, 소셜 로그인 페이지로 redirect와 동시에 인증 요청임을 증명하기 위해 Cookie에 저장 (이 과정을 거치지 않는
     * 경우, 인증 요청 실패 및 OAuth2AuthenticationFailureHandler의 처리 진행 )
     *
     * @param request the {@code HttpServletRequest}
     * @return OAuth2AuthorizationRequest
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
            .orElse(null);
    }

    /**
     * 처음 social login uri로 요청을 진행할 때, redirect-uri param 값을 Cookie에 저장하며, 이는 추후 소셜 로그인이 정상적으로 진행된 후
     * OAuth2AuthenticationSuccessHandler 처리 과정에서 redirect될 uri를 설정하는 과정에서 해당 Cookie가 사용됨
     *
     * @param request the {@code HttpServletRequest}
     */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest,
        HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME,
                redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    /**
     * request, response의 인가 요청 및 redirect-uri cookie 정보를 삭제 처리
     *
     * @param request  the {@code HttpServletRequest}
     * @param response the {@code HttpServletResponse}
     */
    public void removeAuthorizationRequestCookies(HttpServletRequest request,
        HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}
