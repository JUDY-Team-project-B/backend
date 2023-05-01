package com.hangout.hangout.domain.auth.controller;

import com.hangout.hangout.domain.auth.request.LoginReqeust;
import com.hangout.hangout.domain.auth.request.SignUpRequest;
import com.hangout.hangout.domain.auth.response.AuthResponse;
import com.hangout.hangout.domain.auth.service.AuthService;
import com.hangout.hangout.global.error.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Api(value = "/", tags = "AUTH API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value="회원 가입")
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.successResponse(authService.signUp(request));

    }
    @ApiOperation(value="로그인")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginReqeust request) {
            return ResponseEntity.successResponse(authService.login(request));
    }

}
