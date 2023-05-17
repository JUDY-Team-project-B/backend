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

    /**
     * <p> OAuth2의 인증이 정상적으로 완료되었을 때, 회원 정보에 대한 logic 처리
     * @param userRequest access token을 포함한 객체
     * @return OAuth2User
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    /**
     * OAuth2 인증 요청 플랫폼(kakao, google 등)을 구분하고,
     * 이에 맞는 OAuth2UserInfo 객체를 생성하여 logic 처리
     * @param oAuth2UserRequest access token을 포함한 객체
     * @param oAuth2User 리소스 서버에서 받아온 사용자 정보 객체
     * @return UserPrincipal
     */
    public OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest,
        OAuth2User oAuth2User) {
        // social login 구분
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId()
            .toUpperCase();

        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(registrationId,
            oAuth2User.getAttributes());

        // email 정보가 담겨있지 않은 경우
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new AuthException(ResponseType.OAUTH2_USER_NOT_EXIST_EMAIL);
        }

        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
        if (user != null) { // 소셜 로그인으로 이미 회원가입한 경우
            userRepository.save(user.update(oAuth2UserInfo));
        } else {
            // 회원가입 로직을 진행 후, 부족한 user 정보는 따로 채워넣어야 함
            user = registerUser(oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2UserInfo.getAttributes());
    }

    /**
     * OAuth2 인증 요청 플랫폼(kakao, google 등)을 구분하여
     * 이에 맞는 OAuth2UserInfo 객체 생성
     * @param registrationId OAuth2 인증 요청 플랫폼 구분을 위한 값
     * @param attributes 리소스 서버에서 받아온 사용자 정보 객체에 포함된 attribute
     * @return OAuth2UserInfo
     */
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
        Map<String, Object> attributes) {
        switch (registrationId) {
            case "GOOGLE":
                return new GoogleOAuth2User(attributes);

            default:
                throw new AuthException(ResponseType.AUTH_INVALID_PROVIDER);
        }
    }

    /**
     * 소셜 로그인이 처음 진행되었을 때, 회원 가입 logic 처리
     * @param oAuth2UserInfo OAuth2 유저 정보가 포함된 객체
     * @return User
     */
    private User registerUser(OAuth2UserInfo oAuth2UserInfo) {
        String uuid = UUID.randomUUID().toString().substring(0, 6);
        User user = User.builder()
            .email(oAuth2UserInfo.getEmail())
            // password는 social 로그인이기 때문에, 임시로 생성하여 저장
            .password(bCryptPasswordEncoder.encode("password" + uuid))
            .nickname(oAuth2UserInfo.getName())
            .role(Role.USER)
            .oAuth2Id(oAuth2UserInfo.getOAuth2Id())
            .build();
        return userRepository.save(user);
    }
}
