package com.hangout.hangout.domain.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangout.hangout.domain.auth.dto.request.EmailCheckRequest;
import com.hangout.hangout.domain.auth.dto.request.LoginReqeust;
import com.hangout.hangout.domain.auth.dto.request.NicknameCheckRequest;
import com.hangout.hangout.domain.auth.dto.request.SignUpRequest;
import com.hangout.hangout.domain.auth.dto.response.AuthResponse;
import com.hangout.hangout.domain.auth.repository.TokenRepository;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.repository.UserRepository;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.AuthException;
import com.hangout.hangout.global.exception.NotFoundException;
import com.hangout.hangout.global.security.JwtService;
import com.hangout.hangout.global.security.UserPrincipal;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public Long signup(SignUpRequest request) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            throw new AuthException(ResponseType.AUTH_INVALID_EMAIL);
        }
        if (Boolean.TRUE.equals(userRepository.existsByNickname(request.getNickname()))){
            throw new AuthException(ResponseType.AUTH_INVALID_NICKNAME);
        }
        User user = request.toEntity(passwordEncoder);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public Boolean checkEmail(EmailCheckRequest request) {
        return userRepository.existsByEmail(request.getEmail());
    }

    public Boolean checkNickname(NicknameCheckRequest request) {
        return userRepository.existsByNickname(request.getNickname());
    }

    public AuthResponse login(LoginReqeust request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new NotFoundException(ResponseType.USER_NOT_EXIST_EMAIL));
        String jwtToken = jwtService.generateToken(UserPrincipal.create(user));
        String refreshToken = jwtService.generateRefreshToken(UserPrincipal.create(user));
        jwtService.revokeAllUserTokens(user);
        jwtService.saveUserToken(user, refreshToken);
        return createAuthResponse(jwtToken, refreshToken);
    }

    private AuthResponse createAuthResponse(String accessToken, String refreshToken) {
        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Scheduled(fixedRate = 604800000) // 1 week
    @Transactional
    public void deleteExpiredAndRevokedTokens() {
        tokenRepository.deleteExpiredAndRevokedTokens();
    }

    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, UserPrincipal.create(user))) {
                String accessToken = jwtService.generateToken(UserPrincipal.create(user));
                jwtService.revokeAllUserTokens(user);
                jwtService.saveUserToken(user, accessToken);
                var authResponse = AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    /**
     * 소셜 로그인에 따른 처리 과정 분리
     *
     * @param request
     * @param registrationId 소셜 로그인 분리
     * @return
     */
    public AuthResponse redirectLogin(HttpServletRequest request, String registrationId) {
        switch (registrationId.toUpperCase()) {
            case "GOOGLE":
                return redirectGoogleLogin(request);
            default:
                throw new AuthException(ResponseType.AUTH_INVALID_PROVIDER);
        }
    }

    /**
     * google 소셜 로그인 처리, cookie에 담긴 jwt 정보를 AuthResponse로 반환
     *
     * @param request
     * @return AuthResponse
     */
    private AuthResponse redirectGoogleLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jwtToken = "", refreshToken = "";

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                jwtToken = cookie.getValue();
            } else if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        return AuthResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }
}

