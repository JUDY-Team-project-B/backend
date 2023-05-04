package com.hangout.hangout.domain.user.dto;

import com.hangout.hangout.domain.user.entity.Gender;
import com.hangout.hangout.domain.user.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserDto {

  private Long id;
  private String email;
  private String password;
  private Role role;
  private String nickname;
  private String image;
  private String information;
  private Gender gender;
  private int age;

}