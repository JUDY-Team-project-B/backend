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

        postService.updatePost(post, post.getPostInfo(), postRequest);

        return successResponse();
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {
        Post post = postService.findPostById(postId);

        postService.deletePost(post);

        return successResponse();
    }
}
