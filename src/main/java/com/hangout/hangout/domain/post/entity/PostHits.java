package com.hangout.hangout.domain.post.entity;

import com.hangout.hangout.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "POST_HITS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_HITS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ColumnDefault("0")
    private int viewCnt; // 게시글 조회수

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 이게 꼭 필요한가?

    @Builder
    public PostHits(Long id, Post post, User user, int viewCnt,
                    LocalDateTime createdAt) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.viewCnt = viewCnt;
        this.createdAt = createdAt;
    }
}
