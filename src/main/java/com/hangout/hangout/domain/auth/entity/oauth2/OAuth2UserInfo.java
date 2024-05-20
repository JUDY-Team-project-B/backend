package com.hangout.hangout.domain.auth.entity.oauth2;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 추후 social login이 여러 계정으로 분할됨으로, 이를 위한 추상화 class
 */
@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getOAuth2Id();

    public abstract String getEmail();

    public abstract String getName();
}
