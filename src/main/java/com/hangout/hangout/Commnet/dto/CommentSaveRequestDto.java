package com.hangout.hangout.Commnet.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private Long parentId;

    private String content;

    @Builder
    public CommentSaveRequestDto( Long parentId, String content){
        this.parentId = parentId;
        this.content = content;
    }

    public Comment toEntity(){
        return Comment.builder()
                .parentId(parentId)
                .content(content)
                .build();
    }
}
