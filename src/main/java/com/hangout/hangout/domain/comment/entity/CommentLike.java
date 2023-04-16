package com.hangout.hangout.domain.comment.entity;

import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "COMMENT_LIKE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_LIKE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @ColumnDefault("0")
    private int likeCnt; // 추천 수
}
