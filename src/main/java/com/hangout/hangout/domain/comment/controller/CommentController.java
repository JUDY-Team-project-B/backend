package com.hangout.hangout.domain.comment.controller;

import com.hangout.hangout.domain.comment.dto.CommentReadDto;
import com.hangout.hangout.domain.comment.dto.CommentCreateDto;
import com.hangout.hangout.domain.comment.dto.CommentUpdateRequestDto;
import com.hangout.hangout.domain.comment.service.CommentService;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.repository.PostRepository;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.repository.UserRepository;
import com.hangout.hangout.global.common.domain.entity.Status;
import com.hangout.hangout.global.common.domain.repository.StatusRepository;
import com.hangout.hangout.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.hangout.hangout.domain.user.entity.QUser.user;
import static com.hangout.hangout.global.error.ResponseEntity.successResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<HttpStatus> Create(@RequestBody CommentCreateDto
                                                            commentCreateDto, @CurrentUser User user){
        commentService.saveComment(commentCreateDto,user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody CommentUpdateRequestDto
                                                     commentUpdateRequestDto, @PathVariable Long id){
        commentService.updateComment(id,commentUpdateRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/{post_id}")
    public ResponseEntity<List<CommentReadDto>> readComment(@PathVariable Long post_id){
        List<CommentReadDto> comments= commentService.findCommentByPostId(post_id);
        return ResponseEntity.ok(comments);
    }
}