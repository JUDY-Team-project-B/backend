package com.hangout.hangout.Post.presentation;

import com.hangout.hangout.Post.application.PostService;
import com.hangout.hangout.Post.dto.PostRequest;
import com.hangout.hangout.Post.dto.PostResponse;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.global.error.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.hangout.hangout.global.error.ResponseEntity.successResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<HttpStatus> createNewPost(@RequestBody @Valid PostRequest
                                                    postRequest) {
        postService.createNewPost(postRequest);

        return successResponse();
    }

    @GetMapping("/{postId}")
    public org.springframework.http.ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return org.springframework.http.ResponseEntity.ok(PostResponse.of(postService.findPostById(postId)));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<HttpStatus> updatePost(@PathVariable Long postId,
                                                 @RequestBody @Valid PostRequest postRequest) {
        Post post = postService.findPostById(postId);

        // 유저 기능 추가 시 유저가 존재하지 않을 경우의 에러 처리 추가 예정
        postService.updatePost(post, post.getPostInfo(), postRequest);

        return successResponse();
    }
}
