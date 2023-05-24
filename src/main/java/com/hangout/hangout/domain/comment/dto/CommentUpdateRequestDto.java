package com.hangout.hangout.domain.comment.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {
    private String content;

    @Builder
    public CommentUpdateRequestDto(String content){
        this.content = content;
    }

    public Comment toUpdate(){
        return Comment.builder()
                .content(content)
                .build();
    }
}
