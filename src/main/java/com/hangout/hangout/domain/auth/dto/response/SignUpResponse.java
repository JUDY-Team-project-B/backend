package com.hangout.hangout.domain.auth.dto.response;

import com.hangout.hangout.domain.user.entity.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpResponse {
    private String email;
    private String password;
    private String nickname;
    private Gender gender;
    private int age;
}
