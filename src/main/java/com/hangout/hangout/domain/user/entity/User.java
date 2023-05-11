package com.hangout.hangout.domain.user.entity;

import com.hangout.hangout.domain.post.entity.PostHits;
import com.hangout.hangout.global.common.domain.entity.BaseEntity;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.InvalidFormatException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "USER")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 이메일(ID)

    @Column(nullable = false)
    private String password; // 비밀번호

    @OneToMany(mappedBy = "user")
    private List<PostHits> hits = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String image;

    private String description;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    @Builder
    public User(Long id, String email, String password, List<PostHits> hits, Role role,
        String nickname,
        String image, String description, Gender gender, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.hits = hits;
        this.role = role;
        this.nickname = nickname;
        this.image = image;
        this.description = description;
        this.gender = gender;
        this.age = age;
    }

    public void updateNickname(String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    public void updateProfileImage(String image) {
        this.image = image;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    private void validateNickname(final String nickname) {
        if (nickname.length() > 100) {
            throw new InvalidFormatException(ResponseType.INVALID_FORMAT);
        }
    }
}

