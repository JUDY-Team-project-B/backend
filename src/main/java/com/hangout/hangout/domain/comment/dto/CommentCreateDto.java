package com.hangout.hangout.domain.comment.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CommentCreateDto {
    private Long userId;

    private Long postId;

    private Long statusId;

    private Long parentId;

    private String content;
    @Builder
    public CommentCreateDto(Long user, Long post,Long status,Long parentId, String content){
        this.userId = user;
        this.postId = post;
        this.statusId = status;
        this.parentId = parentId;
        this.content = content;
    }


    public Comment toEntity(CommentCreateDto dto, User user, Post post, Status status){
        return Comment.builder()
                .user(user)
                .post(post)
                .status(status)
                .parentId(dto.getParentId())
                .content(dto.getContent())
                .build();
    }
}