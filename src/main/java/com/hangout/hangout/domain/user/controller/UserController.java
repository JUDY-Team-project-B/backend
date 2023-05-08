package com.hangout.hangout.domain.user.controller;

import com.hangout.hangout.domain.user.dto.UserProfileUpdateRequest;
import com.hangout.hangout.domain.user.dto.UserResponse;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.service.UserService;
import com.hangout.hangout.global.config.AuthenticationFacade;
import com.hangout.hangout.global.error.ResponseEntity;
import com.hangout.hangout.global.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private AuthenticationFacade authenticationFacade;

    @GetMapping("/me")
    @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @Operation(summary = "현재 유저의 정보 조회", tags = {"User Controller"})
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal User user) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String name2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println(name);
        System.out.println(name2);
        return ResponseEntity.successResponse(UserResponse.of(user));
    }

    /*
    todo - 유저의 히스토리 조회,
     */

    @PutMapping("/me")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @Operation(summary = "프로필 수정", tags = {"User Controller"})
    public ResponseEntity<UserResponse> updateProfile(@CurrentUser User user,
        @RequestBody @Valid UserProfileUpdateRequest request) {
        userService.updateProfile(user, request);
        return ResponseEntity.successResponse(UserResponse.of(user));
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @Operation(summary = "유저 정보 조회", description = "유저 id에 따른 유저 정보 조회(상대방 프로필 조회 시)",tags = {"User Controller"})
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User userById = userService.getUserById(id);
        return ResponseEntity.successResponse(UserResponse.of(userById));
    }

    @GetMapping("/test")
    public User test() {
        return authenticationFacade.getCurrentUser();
    }

}
