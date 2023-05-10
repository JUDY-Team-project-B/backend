package com.hangout.hangout.domain.user.entity;

import com.hangout.hangout.domain.auth.entity.oauth2.OAuth2UserInfo;
import com.hangout.hangout.domain.post.entity.PostHits;
import com.hangout.hangout.global.common.domain.entity.BaseEntity;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.InvalidFormatException;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 이메일(ID)

    @Column(nullable = false)
    private String password; // 비밀번호

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<PostHits> hits = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String image;

    private String information;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    @Column(unique = true, nullable = false)
    private String oAuth2Id;

    public User update(OAuth2UserInfo oAuth2UserInfo) {
        this.oAuth2Id = oAuth2UserInfo.getOAuth2Id();
        return this;
    }

    public void updateNickName(final String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private void validateNickname(final String nickname) {
        if (nickname.length() > 100) {
            throw new InvalidFormatException(ResponseType.INVALID_FORMAT);
        }
    }

}
