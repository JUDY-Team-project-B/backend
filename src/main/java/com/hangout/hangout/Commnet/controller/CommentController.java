    package com.hangout.hangout.Commnet.controller;


    import com.hangout.hangout.Commnet.dto.CommentSaveRequestDto;
    import com.hangout.hangout.Commnet.dto.CommentUpdateRequestDto;
    import com.hangout.hangout.Commnet.service.CommentService;
    import com.hangout.hangout.domain.comment.entity.Comment;
    import com.hangout.hangout.global.error.ResponseEntity;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.*;

    @RequiredArgsConstructor
    @RestController
    public class    CommentController {
        private final CommentService commentService;
        @PostMapping("/api/v1/comment")
        public ResponseEntity<HttpStatus> saveNewComment(@RequestBody CommentSaveRequestDto
                            commentSaveRequestDto){
            commentService.saveComment(commentSaveRequestDto);

            return ResponseEntity.successResponse();
        }

        @PutMapping("/api/v1/comment/{id}")
        public ResponseEntity<HttpStatus> update(@RequestBody CommentUpdateRequestDto
                                                 commentUpdateRequestDto, @PathVariable Long id){
            commentService.updateComment(id,commentUpdateRequestDto);

            return ResponseEntity.successResponse();
        }
    }
