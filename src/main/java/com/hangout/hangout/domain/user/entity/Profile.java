//package com.hangout.hangout.domain.user.entity;
//
//import com.hangout.hangout.global.common.domain.BaseEntity;
//import com.hangout.hangout.global.error.ResponseType;
//import com.hangout.hangout.global.exception.InvalidFormatException;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Table(name = "PROFILE")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Builder
//public class Profile extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "PROFILE_ID")
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID")
//    private User user;
//
//    // todo 필요여부 논의
////    @Column(nullable = false)
////    private String name;
//
//    @Column(nullable = false, unique = true)
//    private String nickname;
//
//    private String image;
//
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//
//    private int age;
//
//    public void updateNickName(final String nickname) {
//        validateNickname(nickname);
//        this.nickname = nickname;
//    }
//
//    private void validateNickname(final String nickname) {
//        if (nickname.length() > 100) {
//            throw new InvalidFormatException(ResponseType.INVALID_FORMAT);
//        }
//    }
//}
