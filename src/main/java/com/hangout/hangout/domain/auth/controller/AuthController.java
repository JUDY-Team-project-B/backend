package com.hangout.hangout.domain.auth.controller;

import static com.hangout.hangout.global.common.domain.entity.Constants.API_PREFIX;
import static com.hangout.hangout.global.common.domain.entity.Constants.FAILURE_ENDPOINT;

import com.hangout.hangout.domain.auth.dto.request.EmailCheckRequest;
import com.hangout.hangout.domain.auth.dto.request.LoginReqeust;
import com.hangout.hangout.domain.auth.dto.request.NicknameCheckRequest;
import com.hangout.hangout.domain.auth.dto.request.SignUpRequest;
import com.hangout.hangout.domain.auth.dto.response.AuthResponse;
import com.hangout.hangout.domain.auth.service.AuthService;
import com.hangout.hangout.global.error.ResponseEntity;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.AuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequiredArgsConstructor
@Slf4j
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

    @Operation(summary = "이메일 중복확인", tags = {"Auth Controller"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PostMapping("/check/email")
    public ResponseEntity<HttpStatus> checkEmail(@Valid @RequestBody EmailCheckRequest request) {
        if (authService.checkEmail(request)) {
            throw new AuthException(ResponseType.DUPLICATED_EMAIL);
        } else {
            return ResponseEntity.successResponse(request.getEmail() + "은 중복되지 않은 이메일입니다!");
        }
    }

    @Operation(summary = "닉네임 중복확인", tags = {"Auth Controller"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PostMapping("/check/nickname")
    public ResponseEntity<HttpStatus> checkNickname(
        @Valid @RequestBody NicknameCheckRequest request) {
        if (authService.checkNickname(request)) {
            throw new AuthException(ResponseType.DUPLICATED_NICKNAME);
        } else {
            return ResponseEntity.successResponse(request.getNickname() + "은 중복되지 않은 닉네임입니다!");
        }
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

    @GetMapping("/oauth2/callback/{registration}")
    @Operation(summary = "소셜 로그인 성공 redirect",
        description = "소셜 로그인 검증이 모두 끝난 후, jwt를 AuthResponse로 만들어 반환하는 api")
    public ResponseEntity<AuthResponse> redirectLogin(
        @PathVariable String registration,
        HttpServletRequest request
    ) {
        return ResponseEntity.successResponse("google login 완료",
            authService.redirectLogin(request, registration));
    }

    @GetMapping(FAILURE_ENDPOINT)
    @Operation(summary = "소셜 로그인 실패 redirect")
    public ResponseEntity redirectLoginFail(
        @RequestParam String error
    ) {
        return ResponseEntity.failureResponse(ResponseType.FAILURE, error);
    }

}
