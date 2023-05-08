package com.hangout.hangout.Commnet.service;

import com.hangout.hangout.Commnet.domain.repository.CommentRepository;
import com.hangout.hangout.Commnet.dto.CommentSaveRequestDto;
import com.hangout.hangout.Commnet.dto.CommentUpdateRequestDto;
import com.hangout.hangout.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void saveComment(CommentSaveRequestDto comment){
        Comment comment1 = comment.toEntity();
        commentRepository.save(comment1);
    }

    @Transactional
    public void updateComment(Long id,CommentUpdateRequestDto comment){
        Comment comment2 = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당댓글이 존재하지 않습니다."+id ));

        comment2.update(comment.getContent());
    }
}
