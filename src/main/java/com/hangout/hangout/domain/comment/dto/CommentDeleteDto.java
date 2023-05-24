package com.hangout.hangout.domain.comment.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.global.common.domain.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteDto {
    private Status status;
    @Builder
    public CommentDeleteDto(Status status){ this.status = status;}
    public Comment toDelete(Status status){
        return Comment.builder()
                .status(status)
                .build();
    }
}
