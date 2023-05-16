    package com.hangout.hangout.domain.comment.controller;


    import com.hangout.hangout.domain.comment.dto.CommentReadDto;
    import com.hangout.hangout.domain.comment.dto.CommentSaveRequestDto;
    import com.hangout.hangout.domain.comment.dto.CommentUpdateRequestDto;
    import com.hangout.hangout.domain.comment.service.CommentService;
    import com.hangout.hangout.domain.post.entity.Post;
    import com.hangout.hangout.domain.post.repository.PostRepository;
    import com.hangout.hangout.domain.user.entity.User;
    import com.hangout.hangout.global.annotation.LoginMember;
    import com.hangout.hangout.global.common.domain.entity.Status;
    import com.hangout.hangout.global.common.domain.repository.StatusRepository;
    import com.hangout.hangout.global.error.ResponseEntity;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RequiredArgsConstructor
    @RestController
    public class CommentController {
        private final CommentService commentService;
        private final PostRepository postRepository;
        private final StatusRepository statusRepository;
        @PostMapping("/api/v1/comment")
        public ResponseEntity<HttpStatus> saveNewComment(@RequestBody CommentSaveRequestDto
                            commentSaveRequestDto, @LoginMember User user){

            Post post = postRepository.findPostById(commentSaveRequestDto.getPostId()).get();
            Status status = statusRepository.findStatusById(commentSaveRequestDto.getStatusId()).get();
            commentService.saveComment(commentSaveRequestDto, post,status, user);

            return ResponseEntity.successResponse();
        }

        @PutMapping("/api/v1/comment/{id}")
        public ResponseEntity<HttpStatus> update(@RequestBody CommentUpdateRequestDto
                                                 commentUpdateRequestDto, @PathVariable Long id){
            commentService.updateComment(id,commentUpdateRequestDto);

            return ResponseEntity.successResponse();
        }

        @DeleteMapping("api/v1/comment/{id}")
        public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id){
            commentService.deleteComment(id);
            return ResponseEntity.successResponse();

        }
        @GetMapping("api/v1/comment/{postid}/")
        public ResponseEntity<List<CommentReadDto>> readComment(@PathVariable Long postid){
            List<CommentReadDto> comments= commentService.findCommentByPostId(postid);
            return ResponseEntity.successResponse("성공",comments);
        }
    }
