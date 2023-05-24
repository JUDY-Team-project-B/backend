package com.hangout.hangout.domain.comment.service;

import com.hangout.hangout.domain.comment.domain.repository.CommentRepository;
import com.hangout.hangout.domain.comment.dto.CommentReadDto;
import com.hangout.hangout.domain.comment.dto.CommentCreateDto;
import com.hangout.hangout.domain.comment.dto.CommentUpdateRequestDto;
import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void saveComment(CommentCreateDto commentDto,User user, Post post, Status status){
        Comment comment = commentDto.toEntity(commentDto,user,post, status);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id,CommentUpdateRequestDto comment){
        Comment comment2 = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당댓글이 존재하지 않습니다."+id ));

        comment2.update(comment.getContent());
    }

    @Transactional
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }



    @Transactional
    public List<CommentReadDto> findCommentByPostId(Long postid){
        List<Comment> comments = commentRepository.findByPostId(postid);
        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CommentReadDto mapToDTO(Comment comment){
        return CommentReadDto.builder()
                .Id(comment.getId())
                .parentId(comment.getParentId())
                .post(comment.getPost().getId())
                .content(comment.getContent())
                .status(comment.getStatus().getId())
                .build();
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당댓글이 존재하지 않습니다."+id));
    }
}