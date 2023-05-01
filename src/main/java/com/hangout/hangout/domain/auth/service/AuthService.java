package com.hangout.hangout.domain.auth.service;

import com.hangout.hangout.domain.auth.request.LoginReqeust;
import com.hangout.hangout.domain.auth.request.SignUpRequest;
import com.hangout.hangout.domain.auth.response.AuthResponse;
import com.hangout.hangout.domain.user.entity.Role;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.repository.UserRepository;
import com.hangout.hangout.global.config.JwtService;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signUp(SignUpRequest request) {
        User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .nickname(request.getNickname())
            .gender(request.getGender())
            .role(Role.USER)
            .age(request.getAge())
            .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
            .token(jwtToken)
            .build();

    }

    public AuthResponse login(LoginReqeust request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(()-> new NotFoundException(ResponseType.USER_NOT_EXIST_EMAIL));

        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
            .token(jwtToken)
            .build();
    }
}

