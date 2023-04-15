package com.hangout.hangout.domain.post.entity;

import com.hangout.hangout.global.common.domain.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "POST_TAG_REL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTagRel { // 게시물과 태그의 many to many로 인한 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_TAG_REL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @Builder
    public PostTagRel(Long id, Post post, Tag tag) {
        this.id= id;
        this.post = post;
        this.tag = tag;
    }
}
