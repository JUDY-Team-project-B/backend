package com.hangout.hangout.Commnet.controller;


import com.hangout.hangout.Commnet.dto.CommentSaveRequestDto;
import com.hangout.hangout.Commnet.service.CommentService;
import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.global.error.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/v1/comment")
    public ResponseEntity<HttpStatus> saveNewComment(@RequestBody CommentSaveRequestDto
                        commentSaveRequestDto){
        commentService.saveComment(commentSaveRequestDto);

        return ResponseEntity.successResponse();

    }

}
