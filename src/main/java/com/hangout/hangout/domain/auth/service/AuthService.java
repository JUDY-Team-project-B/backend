package com.hangout.hangout.domain.auth.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangout.hangout.domain.auth.dto.request.LoginReqeust;
import com.hangout.hangout.domain.auth.entity.Token;
import com.hangout.hangout.domain.auth.entity.TokenType;
import com.hangout.hangout.domain.auth.repository.TokenRepository;
import java.util.List;
import org.springframework.http.HttpHeaders;
import com.hangout.hangout.domain.auth.dto.request.SignUpRequest;
import com.hangout.hangout.domain.auth.dto.response.AuthResponse;
import com.hangout.hangout.domain.user.entity.Role;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.repository.UserRepository;
import com.hangout.hangout.global.config.JwtService;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.AuthException;
import com.hangout.hangout.global.exception.NotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Long signup(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException(ResponseType.USER_NOT_EXIST_EMAIL);
        }
        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .nickname(request.getNickname())
            .gender(request.getGender())
            .role(Role.USER)
            .age(request.getAge())
            .build();
        User savedUser = userRepository.save(user);
        return savedUser.getId();
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

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);
        return AuthResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }

    // refresh token 저장
    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        if (userEmail != null) {
            User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}

