package com.hangout.hangout.domain.image.controller;

import static com.hangout.hangout.global.common.domain.entity.Constants.API_PREFIX;
import static com.hangout.hangout.global.error.ResponseEntity.successResponse;

import com.hangout.hangout.domain.image.service.ImageFileUploadService;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.service.PostService;
import com.hangout.hangout.global.error.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/post")
public class PostImageController {
    private final ImageFileUploadService imageFileUploadService;

    private final PostService postService;

    @PostMapping("/{postId}/images")
    public ResponseEntity<HttpStatus> uploadImages(@PathVariable Long postId,
                                                   @RequestParam("file")List<MultipartFile> files) throws IOException {
        Post post = postService.findPostById(postId);

        imageFileUploadService.upload (files,post);

        return successResponse();
    }

    @DeleteMapping("/{postId}/images")
    public ResponseEntity<HttpStatus> deleteImages(@PathVariable Long postId) {

        Post post = postService.findPostById(postId);

        imageFileUploadService.delete(post);
        return successResponse("게시글 이미지 삭제 성공!");
    }
}
