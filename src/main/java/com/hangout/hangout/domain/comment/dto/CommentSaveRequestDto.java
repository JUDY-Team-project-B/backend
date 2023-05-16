package com.hangout.hangout.domain.comment.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private Long postId;

    private Long statusId;

    private Long parentId;

    private String content;

    @Builder
    public CommentSaveRequestDto(Long post,Long status,Long parentId, String content){
        this.postId = post;
        this.statusId = status;
        this.parentId = parentId;
        this.content = content;
    }

    public Comment toEntity(CommentSaveRequestDto Dto,Post post, Status status,User user){
        return Comment.builder()
                .post(post)
                .status(status)
                .parentId(Dto.getParentId())
                .content(Dto.getContent())
                .user(user)
                .build();
    }
}
