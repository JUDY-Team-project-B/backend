package com.hangout.hangout.domain.comment.service;

import com.hangout.hangout.domain.comment.domain.repository.CommentRepository;
import com.hangout.hangout.domain.comment.dto.CommentDeleteDto;
import com.hangout.hangout.domain.comment.dto.CommentReadDto;
import com.hangout.hangout.domain.comment.dto.CommentCreateDto;
import com.hangout.hangout.domain.comment.dto.CommentUpdateDto;
import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.repository.PostRepository;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.Status;
import com.hangout.hangout.global.common.domain.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hangout.hangout.domain.comment.entity.QComment.comment;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public void saveComment(CommentCreateDto commentDto,User user){
        Post post = postRepository.findPostById(commentDto.getPostId()).get();
        Status status = statusRepository.findStatusById(1L).get();
        Comment comment = commentDto.toEntity(commentDto,user,post,status);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentUpdateDto comment){
        Comment comment2 = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당댓글이 존재하지 않습니다."+id ));

        comment2.update(comment.getContent());
    }

    @Transactional
    public void deleteComment(Long id, CommentDeleteDto comment){
        Comment comment2 = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당댓글이 존재하지 않습니다."+id ));
        comment2.delete(comment.getStatus());
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

    @Transactional
    public List<CommentReadDto> getAllCommentsByPost(Long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentReadDto> commentReadDtoList = new ArrayList<>();
        Map<Long, CommentReadDto> commentDTOHashMap = new HashMap<>();

        for (Comment comment : comments) {
            CommentReadDto commentReadDto = convertCommentTODto(comment);
            commentDTOHashMap.put(commentReadDto.getId(),commentReadDto);
            if(comment.getParent() !=null){
                commentDTOHashMap.get(comment.getParent().getId());
            }else{
                commentReadDtoList.add(commentReadDto);
            }
        }
        return commentReadDtoList;

    }

    private CommentReadDto convertCommentTODto(Comment comment){
        CommentReadDto dto = new CommentReadDto(comment.getId(),comment.getPost(),comment.getStatus(),comment.getContent());
        return dto;
    }


}