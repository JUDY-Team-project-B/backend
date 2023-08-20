package com.hangout.hangout.domain.auth.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NicknameCheckRequest {
    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Length(max = 10, message = "닉네임은 최대 10자를 넘을 수 없습니다.")
    private String nickname;
}
