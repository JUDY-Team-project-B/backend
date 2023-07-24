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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "COMMENT")
@DynamicInsert
@DynamicUpdate
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

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY ,  orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    // 신고 기능 추가를 위해 commentReport 와 연결
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<CommentReport> commentReports = new ArrayList<>();

    @Lob
    @Column(name = "CONTENT", nullable = false)
    private String content;


    public void setParent(Comment comment){this.parent = comment;}

    public void setStatus(Status status) {
        this.status = status;
    }

    public void update(String content){
        this.content = content;
    }

    @Builder
    public Comment(User user, Post post, Status status, Long parentId, Integer likeCount ,String content) {
        this.user = user;
        this.post = post;
        this.status = status;
        this.parentId = parentId;
        this.likeCount = likeCount;
        this.content = content;
    }

}
