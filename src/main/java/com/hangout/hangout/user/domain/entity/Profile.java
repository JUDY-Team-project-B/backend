package com.hangout.hangout.user.domain.entity;

import com.hangout.hangout.global.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "PROFILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name="NAME",nullable = false)
    private String name;
    @Column(name="NICKNAME",nullable = false)
    private String nickname;
    private String image; // 이미지의 URL 저장 // 리스트로 저장해야 하나?
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;

    @Builder
    public Profile(User user,String name, String nickname, String image
            ,String email, Gender gender, int age) {
        this.user =user;
        this.name = name;
        this.nickname = nickname;
        this.image = image;
        this.email = email;
        this.gender= gender;
        this.age = age;
    }
}
