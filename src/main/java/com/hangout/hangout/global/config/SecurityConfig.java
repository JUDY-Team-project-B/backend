package com.hangout.hangout.global.config;

import static com.hangout.hangout.global.common.domain.entity.Constants.AUTHORIZATION_ENDPOINT;
import static com.hangout.hangout.global.common.domain.entity.Constants.PERMIT_ALL_URI_LIST;
import static com.hangout.hangout.global.common.domain.entity.Constants.PERMIT_GET_URI_LIST;

import com.hangout.hangout.domain.user.service.CustomOAuth2UserService;
import com.hangout.hangout.global.common.domain.repository.CookieAuthorizationRequestRepository;
import com.hangout.hangout.global.handler.JwtAccessDeniedHandler;
import com.hangout.hangout.global.handler.JwtAuthenticationEntryPoint;
import com.hangout.hangout.global.handler.OAuth2AuthenticationFailureHandler;
import com.hangout.hangout.global.handler.OAuth2AuthenticationSuccessHandler;
import com.hangout.hangout.global.security.JwtAuthenticateFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticateFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /**
     * spring security + jwt + OAuth2 사용을 위한 설정
     * <p>1. httpBasic, csrf, formLogin, rememberMe, logout, session 사용 x
     * <p>2. 요청, swagger에 대한 권한 설정 및 jwt filter 설정
     * <p>3. logout에 대한 후처리
     * <p>4. OAuth2 login 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .formLogin().disable()
            .cors().configurationSource(corsConfigurationSource()).and()
            .httpBasic().disable()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .rememberMe().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
            .authorizeHttpRequests()
            .antMatchers(PERMIT_ALL_URI_LIST)
            .permitAll()
            .antMatchers(HttpMethod.GET, PERMIT_GET_URI_LIST)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .addLogoutHandler(logoutHandler)
            .clearAuthentication(true)
            .logoutSuccessHandler(
                (request, response, authentication) -> SecurityContextHolder.clearContext())
            .deleteCookies("JSESSIONID");

        http
            .formLogin().disable()
            .oauth2Login()
            .userInfoEndpoint().userService(customOAuth2UserService)
            .and()
            .authorizationEndpoint().baseUri(AUTHORIZATION_ENDPOINT)
            .authorizationRequestRepository(cookieAuthorizationRequestRepository)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler);

        return http.build();
    }

    /**
     * cors error를 방지하기 위한 설정으로, spring security의 cors configuration 적용
     *
     * <p>{@code AllowedOriginPattern} : 자원 공유를 허락할 Origin 지정으로, 외부에서 들어오는 허용 url 설정
     * <p>{@code allowedMethod} : 허용 HTTP method
     * <p>{@code allowCredentials} : 자격증명 허용
     * <p>{@code allowedHeader} : 허용 HTTP header
     */

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(CorsConfiguration.ALL));
        configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "TRACE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader(CorsConfiguration.ALL);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}