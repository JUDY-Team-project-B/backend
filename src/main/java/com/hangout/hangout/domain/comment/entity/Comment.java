package com.hangout.hangout.domain.comment.entity;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.report.entity.CommentReport;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.BaseEntity;
import com.hangout.hangout.global.common.domain.entity.Status;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "COMMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    private Status status;

    // 40 ~ 48 : Self Join
    @Column(name = "PARENT_ID")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "COMMENT_ID", insertable = false, updatable = false)
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> children = new ArrayList<>();

    // 좋아요 기능 추가를 위해 commentLikes 와 연결
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<CommentLike> commentLikes = new ArrayList<>();

    // 신고 기능 추가를 위해 commentReport 와 연결
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<CommentReport> commentReports = new ArrayList<>();

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private String content;

    public void setContent(String content){this.content = content;}

    public void update(String content){
        this.content = content;
    }

    public void delete(Status status){this.status=status;}

    @Builder
    public Comment(User user, Post post, Status status, Long parentId, String content) {
        this.user = user;
        this.post = post;
        this.status = status;
        this.parentId = parentId;
        this.content = content;
    }

}
