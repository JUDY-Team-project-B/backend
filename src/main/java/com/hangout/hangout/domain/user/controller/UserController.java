package com.hangout.hangout.domain.user.controller;

import com.hangout.hangout.domain.user.dto.UserProfileUpdateRequest;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.service.UserService;
import com.hangout.hangout.global.config.AuthenticationFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "/", tags = "USER API")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final AuthenticationFacade authenticationFacade;

  @PutMapping("/me")
  @ApiOperation(value = "프로필 수정")
  public void updateProfile(@Valid @RequestBody UserProfileUpdateRequest request) {
    User user = authenticationFacade.getCurrentUser();
    if (request.getNickname() != null) {
      user.updateNickname(request.getNickname());
    }
    if (request.getImage() != null) {
      user.updateProfileImage(request.getImage());
    }
    if (request.getDescription() != null) {
      user.updateDescription(request.getDescription());
    }
    // todo
  }
}
