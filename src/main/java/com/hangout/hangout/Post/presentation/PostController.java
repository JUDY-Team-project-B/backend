package com.hangout.hangout.Post.presentation;

import com.hangout.hangout.Post.application.PostService;
import com.hangout.hangout.Post.dto.PostCreateRequest;
import com.hangout.hangout.domain.user.entity.User;
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
    public ResponseEntity<HttpStatus> createNewPost(@RequestBody @Valid PostCreateRequest
                                                    postCreateRequest) {
        postService.createNewPost(postCreateRequest);

        return successResponse();
    }
}
