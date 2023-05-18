package com.hangout.hangout.domain.auth.controller;

import com.hangout.hangout.domain.auth.dto.request.LoginReqeust;
import com.hangout.hangout.domain.auth.dto.request.SignUpRequest;
import com.hangout.hangout.domain.auth.dto.response.AuthResponse;
import com.hangout.hangout.domain.auth.service.AuthService;
import com.hangout.hangout.global.error.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", tags = {"Auth Controller"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.successResponse("회원가입 성공", authService.signup(request));
    }

    @Operation(summary = "로그인", tags = {"Auth Controller"})
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginReqeust request) {
        return ResponseEntity.successResponse("로그인 성공", authService.login(request));
    }

    @Operation(summary = "token 재발급", tags = {"Auth Controller"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request,
        HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
        return ResponseEntity.successResponse("access token 갱신");
    }
}
