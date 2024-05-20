package com.hangout.hangout.domain.image.controller;

import static com.hangout.hangout.global.common.domain.entity.Constants.API_PREFIX;
import static com.hangout.hangout.global.error.ResponseEntity.successResponse;

import com.hangout.hangout.domain.image.service.UserImageFileUploadService;
import com.hangout.hangout.global.error.ResponseEntity;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/user")
@Slf4j
public class UserImageController {

    private final UserImageFileUploadService userImageFileUploadService;

    @PostMapping("/{userId}/image")
    public ResponseEntity<HttpStatus> uploadImages(@PathVariable Long userId,
        @RequestParam("file") List<MultipartFile> files) throws IOException {
        userImageFileUploadService.upload(userId, files);

        return successResponse();
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<HttpStatus> updateImages(@PathVariable Long userId,
        @RequestParam("file") List<MultipartFile> files) throws IOException {
        userImageFileUploadService.delete(userId);
        userImageFileUploadService.upload(userId, files);

        return successResponse("프로필 이미지 수정 성공!");
    }

    @DeleteMapping("/{userId}/image")
    public ResponseEntity<HttpStatus> deleteImages(@PathVariable Long userId) {
        userImageFileUploadService.delete(userId);

        return successResponse("프로필 이미지 삭제 성공!");
    }
}
