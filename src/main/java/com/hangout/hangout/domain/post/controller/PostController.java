package com.hangout.hangout.domain.post.controller;

import com.hangout.hangout.domain.post.PostMapper;
import com.hangout.hangout.domain.post.service.PostService;
import com.hangout.hangout.domain.post.dto.PostListResponse;
import com.hangout.hangout.domain.post.dto.PostRequest;
import com.hangout.hangout.domain.post.dto.PostResponse;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.global.error.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.hangout.hangout.global.error.ResponseEntity.successResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    private final PostMapper mapper;

    @PostMapping
    public ResponseEntity<HttpStatus> createNewPost(@RequestBody @Valid PostRequest
                                                    postRequest) {
        postService.createNewPost(postRequest);

        return successResponse();
    }

    @GetMapping("/{postId}")
    public org.springframework.http.ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return org.springframework.http.ResponseEntity.ok(mapper.of(postService.findPostById(postId)));
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<List<PostListResponse>> getPosts(@PathVariable Integer page,
                                                     @RequestParam(defaultValue = "8") Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        List<PostListResponse> posts = postService.getPosts(pageRequest);

        return successResponse("게시물 조회에 성공하셨습니다!", posts);
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
