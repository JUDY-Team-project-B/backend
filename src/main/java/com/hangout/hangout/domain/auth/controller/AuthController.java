package com.hangout.hangout.domain.auth.controller;

import static com.hangout.hangout.global.common.domain.entity.Constants.API_PREFIX;

import com.hangout.hangout.domain.auth.dto.request.LoginReqeust;
import com.hangout.hangout.domain.auth.dto.request.SignUpRequest;
import com.hangout.hangout.domain.auth.dto.response.AuthResponse;
import com.hangout.hangout.domain.auth.service.AuthService;
import com.hangout.hangout.global.error.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_PREFIX + "/auth")
@Api(value = "/", tags = "AUTH API")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.successResponse("회원가입 성공", authService.signup(request));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginReqeust request) {
        return ResponseEntity.successResponse("로그인 성공", authService.login(request));
    }

    @ApiOperation(value = "token 재발급")
    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
        return ResponseEntity.successResponse("access token 갱신");
    }

    @GetMapping("/login/oauth2/code/{registrationId}")
    @ApiOperation(value = "구글 로그인 시 redirect url로 수신이 잘 되는지 테스트")
    public org.springframework.http.ResponseEntity<Void> redirectGoogleLogin(
        @RequestParam String code,
        @PathVariable String registrationId) {
        log.info("social: " + registrationId + ", code: " + code);
        return org.springframework.http.ResponseEntity.status(HttpStatus.OK).build();
    }

}
