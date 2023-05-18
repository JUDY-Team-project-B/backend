package com.hangout.hangout.domain.user.dto;

import com.hangout.hangout.domain.user.entity.Gender;
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
public class UserProfileUpdateRequest {
  private String nickname;
  private String image;
  private String description;
  private Gender gender;
  private Integer age;
}
