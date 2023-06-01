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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

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

    private String getExceptionMessage(AuthenticationException exception) {
        if (exception instanceof BadCredentialsException) {
            return "Password is incorrect";
        } else if (exception instanceof UsernameNotFoundException) {
            return "No account";
        } else if (exception instanceof AccountExpiredException) {
            return "Account expiration";
        } else if (exception instanceof CredentialsExpiredException) {
            return "Password expiration";
        } else if (exception instanceof DisabledException) {
            return "Account Deactivation";
        } else if (exception instanceof LockedException) {
            return "Account locked";
        } else {
            return "no confirmed errors";
        }
    }
}
