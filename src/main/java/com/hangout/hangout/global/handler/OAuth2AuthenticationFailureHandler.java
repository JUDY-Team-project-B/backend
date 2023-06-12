package com.hangout.hangout.global.handler;

import static com.hangout.hangout.global.common.domain.entity.Constants.FAILURE_REDIRECT;
import static com.hangout.hangout.global.common.domain.repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import com.hangout.hangout.global.common.domain.repository.CookieAuthorizationRequestRepository;
import com.hangout.hangout.global.util.CookieUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    /**
     * OAuth2 인증 실패 시, failure-redirect-url 로 error 정보 전달 처리
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication request.
     * @throws IOException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException {
        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue).orElse(FAILURE_REDIRECT);

        if (response.isCommitted()) {
            log.debug("Response has already been committed.");
            return;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .encode(StandardCharsets.UTF_8)
            .queryParam("error", getExceptionMessage(exception))
            .build().toUriString();

        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * error message 분석 및 반환
     *
     * @param exception 인증 에러 정보
     * @return String (인증 에러 해석 메시지)
     */
    private String getExceptionMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "패스워드가 일치하지 않습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            return "유저 계정이 존재하지 않습니다";
        } else if (exception instanceof AccountExpiredException) {
            return "계정이 만료되었습니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            return "패스워드가 만료되었습니다.";
        } else if (exception instanceof DisabledException) {
            return "계정이 비활성화되어있습니다";
        } else if (exception instanceof LockedException) {
            return "계정이 잠겨있습니다";
        } else if (exception instanceof OAuth2AuthenticationException) {
            return "인가되지 않은 경로의 요청입니다. 로그인 요청을 다시 진행해주세요";
        } else {
            return exception.getMessage();
        }
    }
}
