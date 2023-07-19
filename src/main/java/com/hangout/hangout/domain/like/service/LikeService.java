package com.hangout.hangout.domain.like.service;

import com.hangout.hangout.domain.comment.domain.repository.CommentRepository;
import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.like.dto.LikeCommentRequest;
import com.hangout.hangout.domain.like.dto.LikeRequest;
import com.hangout.hangout.domain.like.entity.CommentLike;
import com.hangout.hangout.domain.like.entity.PostLike;
import com.hangout.hangout.domain.like.repository.LikeCommentRepository;
import com.hangout.hangout.domain.like.repository.LikeRepository;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.repository.PostRepository;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.repository.UserRepository;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.NotFoundException;
import com.hangout.hangout.global.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Transactional
    public void insert(LikeRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ResponseType.USER_NOT_EXIST_ID));
        Post post = postRepository.findPostById(request.getPostId())
                .orElseThrow(() -> new PostNotFoundException(ResponseType.POST_NOT_FOUND));
        Optional<PostLike> postLike = likeRepository.findByUserAndPost(user, post);

        if (postLike.isEmpty()){
            PostLike like = PostLike.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(like);
            postRepository.addLikeCount(post);
            postRepository.save(post);
        } else {
            likeRepository.deleteByUserAndPost(user, post);
            postRepository.subLikeCount(post);
            postRepository.save(post);
        }
    }

    @Transactional
    public void insert(LikeCommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ResponseType.USER_NOT_EXIST_ID));
        Comment comment = commentRepository.findCommentById(request.getCommentId())
                .orElseThrow(() -> new NotFoundException(ResponseType.COMMENT_NOT_FOUND));
        Optional<CommentLike> commentLike = likeCommentRepository.findByUserAndComment(user, comment);

        if(commentLike.isEmpty()) {
            CommentLike like = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            likeCommentRepository.save(like);
            commentRepository.addLikeCount(comment);
        } else {
            likeCommentRepository.deleteByUserAndComment(user, comment);
            commentRepository.subLikeCount(comment);
        }
    }
}
