package com.hangout.hangout.domain.auth.entity.oauth2;

import java.util.Map;

/**
 * spring security로 전달하는 userPrincipal class에 정보를 전달하기 위한 class
 */
public class GoogleOAuth2User extends OAuth2UserInfo{

    public GoogleOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getOAuth2Id() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
