package com.hangout.hangout.domain.post.entity;

import com.hangout.hangout.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "POST_LIKE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_LIKE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ColumnDefault("0")
    private int likeCnt; // 게시글 좋아요 개수 default 0

    @Builder
    public PostLike(Long id, Post post, User user, int likeCnt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.likeCnt = likeCnt;
    }
}
