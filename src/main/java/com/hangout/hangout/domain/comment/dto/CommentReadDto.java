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
public class CommentReadDto {

    private Long Id;

    private Long postId;

    private Long statusId;

    private Long parentId;

    private String content;

    @Builder
    public CommentReadDto(Long Id,Long post,Long status,Long parentId, String content) {
        this.Id = Id;
        this.postId = post;
        this.statusId = status;
        this.parentId = parentId;
        this.content = content;
    }


    public CommentReadDto toRead(){
        return CommentReadDto.builder()
                .Id(Id)
                .post(postId)
                .status(statusId)
                .parentId(parentId)
                .content(content)
                .build();

    }
}