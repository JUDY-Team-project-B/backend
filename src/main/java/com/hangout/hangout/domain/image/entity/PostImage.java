package com.hangout.hangout.domain.image.entity;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.global.common.domain.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_IMAGE_ID")
    private Long id;
    @Column(name = "POST_IMAGE_NAME")
    private String name;
    @Column(name = "POST_IMAGE_URL")
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder
    public PostImage(String name, String url, Post post) {
        this.name = name;
        this.url = url;
        this.post = post;
    }

}
