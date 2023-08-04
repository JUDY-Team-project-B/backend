package com.hangout.hangout.domain.comment.service;

import com.hangout.hangout.domain.comment.domain.repository.CommentRepository;
import com.hangout.hangout.domain.comment.dto.*;
import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.like.dto.LikeCommentRequest;
import com.hangout.hangout.domain.like.entity.CommentLike;
import com.hangout.hangout.domain.like.repository.LikeCommentRepository;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.repository.PostRepository;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.service.UserService;
import com.hangout.hangout.global.common.domain.entity.Status;
import com.hangout.hangout.global.common.domain.repository.StatusRepository;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.NotFoundException;
import com.hangout.hangout.global.exception.StatusNotFoundException;
import com.hangout.hangout.global.exception.UnAuthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

//import static com.hangout.hangout.domain.comment.entity.Comment.comment;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final StatusRepository statusRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Transactional
    public void saveComment(CommentCreateDto commentCreateDto,User user){
        Post post = postRepository.findPostById(commentCreateDto.getPostId()).orElseThrow(() ->
                new NotFoundException(ResponseType.POST_NOT_FOUND));
        Comment comment = commentCreateDto.toEntity(commentCreateDto,user,post);

        Long newStatus = 1L;
        Status status = statusRepository.findStatusById(newStatus).orElseThrow(
                () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));

        comment.setStatus(status);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, CommentUpdateDto commentUpdateDto, User user){
        Comment comment = commentRepository.findCommentById(id).orElseThrow(() ->
                new NotFoundException(ResponseType.COMMENT_NOT_FOUND));

        if(isMatchedNickname(comment, user)) {
            comment.update(commentUpdateDto.getContent());
        }
    }

    @Transactional
    public void deleteComment(Long id, User user){
        Comment comment = commentRepository.findCommentById(id).orElseThrow(() ->
                new NotFoundException(ResponseType.COMMENT_NOT_FOUND));

        if(isMatchedNickname(comment, user)) {
            Long deleteStatus = 2L;
            Status status = statusRepository.findStatusById(deleteStatus).orElseThrow(
                    () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));

            comment.setStatus(status);
            commentRepository.save(comment);
        }
    }

    public boolean isMatchedNickname(Comment comment, User user) {
        String userNickname = user.getNickname();

        if (!comment.getUser().getNickname().equals(userNickname)) {
            throw new UnAuthorizedAccessException(ResponseType.REQUEST_NOT_VALID);
        }
        return true;
    }

    public int findLike(User user, LikeCommentRequest request) {
        Long commentId = request.getCommentId();

        Comment comment2 = commentRepository.findCommentById(commentId).orElseThrow(() ->
                new NotFoundException(ResponseType.COMMENT_NOT_FOUND));
        // 좋아요 상태가 아니면 0, 맞다면 1
        Optional<CommentLike> findLike = likeCommentRepository.findByUserAndComment(user, comment2);

        if (findLike.isEmpty()) return 0;
        else return 1;
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
                //.post(comment.getPost().getId())
                .content(comment.getContent())
                .build();
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ResponseType.COMMENT_NOT_FOUND));
    }

    public List<CommentRequestDTO> getCommentsByUser(User user) {
        List<Comment> comments = commentRepository.findCommentByUser(user);
        List<CommentRequestDTO> commentRequestDTOList = new ArrayList<>();
        Map<Long, CommentRequestDTO> commentDTOHashMap = new HashMap<>();

        for (Comment comment : comments) {
            CommentRequestDTO commentRequestDTO = convertCommentTODto(comment);
            commentDTOHashMap.put(commentRequestDTO.getId(),commentRequestDTO);
            if(comment.getParent() !=null){
                commentDTOHashMap.get(comment.getParent().getId()).getChildren().add(commentRequestDTO);
            }else{
                commentRequestDTOList.add(commentRequestDTO);
            }
        }
        return commentRequestDTOList;
    }

    @Transactional
    public List<CommentRequestDTO> getAllCommentsByPost(Long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentRequestDTO> commentRequestDTOList = new ArrayList<>();
        Map<Long, CommentRequestDTO> commentDTOHashMap = new HashMap<>();

        for (Comment comment : comments) {
            CommentRequestDTO commentRequestDTO = convertCommentTODto(comment);
            commentDTOHashMap.put(commentRequestDTO.getId(),commentRequestDTO);
            if(comment.getParent() !=null){
                commentDTOHashMap.get(comment.getParent().getId()).getChildren().add(commentRequestDTO);
            }else{
                commentRequestDTOList.add(commentRequestDTO);
            }
        }
        return commentRequestDTOList;
    }

    private CommentRequestDTO convertCommentTODto(Comment comment){
        return new CommentRequestDTO(comment.getId(),comment.getUser(), comment.getContent(), comment.getLikeCount());
    }

}