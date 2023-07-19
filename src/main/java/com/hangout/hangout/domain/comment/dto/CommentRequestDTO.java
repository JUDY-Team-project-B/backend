package com.hangout.hangout.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {
    private Long Id;
    @JsonIgnore
    private User user;
    private String content;
    private Integer likeCount;
    private List<CommentRequestDTO> children;

    public CommentRequestDTO(Long Id,User user,String content,Integer likeCount) {
        this.Id = Id;
        this.user = user;
        this.content = content;
        this.likeCount = likeCount;
    }
    public List<CommentRequestDTO> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }



    public CommentRequestDTO convertCommentTODto(Comment comment){
        return new CommentRequestDTO(comment.getId(),comment.getUser(), comment.getContent(), comment.getLikeCount());
    }
}