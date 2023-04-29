package com.hangout.hangout.domain.user.entity;

import com.hangout.hangout.domain.post.entity.PostHits;
import com.hangout.hangout.global.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "EMAIL" , nullable = false)
    private String email; // 이메일(ID)

    @Column(name = "PASSWORD", nullable = false)
    private String password; // 비밀번호

    // ManyToMany 관계를 위해서 일대다로 관계맺어준 컬럼
    @OneToMany(mappedBy = "user")
    private List<PostHits> hits = new ArrayList<>();

    @Builder
    public User(String email, String password) {
        this.email=email;
        this.password= password;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
}
