package com.hangout.hangout.domain.comment.dto;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentReadDto {

    private Long Id;

    private User user;

    private Long parentId;

    private String content;

    private List<Comment> children;

    @Builder
    public CommentReadDto(Long Id, Long parentId, String content, List<Comment> children ) {
        this.Id = Id;
        this.parentId = parentId;
        this.content = content;
        this.children = children;
    }





    public CommentReadDto toRead(){
        return CommentReadDto.builder()
                .Id(Id)
                .parentId(parentId)
                .content(content)
                .children(children)
                .build();

    }

}