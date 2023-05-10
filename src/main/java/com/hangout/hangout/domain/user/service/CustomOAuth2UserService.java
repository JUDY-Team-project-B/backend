package com.hangout.hangout.domain.user.service;

import com.hangout.hangout.domain.auth.entity.oauth2.UserPrincipal;
import com.hangout.hangout.domain.user.entity.Role;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.domain.auth.entity.oauth2.GoogleOAuth2User;
import com.hangout.hangout.domain.auth.entity.oauth2.OAuth2UserInfo;
import com.hangout.hangout.domain.user.repository.UserRepository;
import com.hangout.hangout.global.exception.AuthException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService
    implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    public OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest,
        OAuth2User oAuth2User) {
        // social login 구분
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId()
            .toUpperCase();

        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(registrationId,
            oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new AuthException(ResponseType.AUTH_OAUTH2_USER_NOT_EXIST_EMAIL);
        }

        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
        if (user != null) {
            userRepository.save(user.update(oAuth2UserInfo));
        } else {
            // 회원가입 로직을 진행 후, 부족한 user 정보는 따로 채워넣어야 함
            user = registerUser(oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2UserInfo.getAttributes());
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
        Map<String, Object> attributes) {
        switch (registrationId) {
            case "GOOGLE":
                return new GoogleOAuth2User(attributes);

            default:
                throw new AuthException(ResponseType.AUTH_INVALID_PROVIDER);
        }
    }

    private User registerUser(OAuth2UserInfo oAuth2UserInfo) {
        String uuid = UUID.randomUUID().toString().substring(0, 6);
        User user = User.builder()
            .email(oAuth2UserInfo.getEmail())
            // password는 social 로그인이기 때문에, 임시로 생성하여 저장
            .password(bCryptPasswordEncoder.encode("password" + uuid))
            .nickname(oAuth2UserInfo.getName())
            .role(Role.USER)
            .build();
        return userRepository.save(user);
    }
}
