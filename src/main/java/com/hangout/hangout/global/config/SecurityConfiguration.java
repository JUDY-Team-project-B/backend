package com.hangout.hangout.global.config;

import static com.hangout.hangout.global.common.domain.entity.Constants.API_PREFIX;
import static com.hangout.hangout.global.common.domain.entity.Constants.SWAGGER_URI_LIST;

import com.hangout.hangout.domain.user.service.CustomOAuth2UserService;
import com.hangout.hangout.global.common.domain.repository.CookieAuthorizationRequestRepository;
import com.hangout.hangout.global.handler.OAuth2AuthenticationFailureHandler;
import com.hangout.hangout.global.handler.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticateFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    /**
     * spring security + jwt + OAuth2 사용을 위한 설정
     * <p>1. httpBasic, csrf, formLogin, rememberMe, logout, session 사용 x
     * <p>2. 요청, swagger에 대한 권한 설정 및 jwt filter 설정
     * <p>3. OAuth2 login 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .rememberMe().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
            .authorizeHttpRequests()
            .requestMatchers(request -> request.getServletPath().startsWith("/api/v1/auth"))
            .permitAll()
            .and()
            .authorizeHttpRequests()
            .antMatchers(SWAGGER_URI_LIST).permitAll()
            .anyRequest().authenticated()
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin().disable()
            .oauth2Login()
            .authorizationEndpoint().baseUri(API_PREFIX + "/auth/oauth2/authorize")
            .authorizationRequestRepository(cookieAuthorizationRequestRepository)
            .and()
            .redirectionEndpoint().baseUri(API_PREFIX + "/auth/oauth2/login/code/*")
            .and()
            .userInfoEndpoint().userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler);

        http
            .logout()
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID");

        return http.build();
    }
}
