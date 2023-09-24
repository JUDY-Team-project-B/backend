package com.hangout.hangout.domain.image.service;

import com.hangout.hangout.domain.image.entity.UserImage;
import com.hangout.hangout.domain.image.repository.ImageJdbcRepository;
import com.hangout.hangout.domain.image.repository.UserImageRepository;
import com.hangout.hangout.domain.image.util.FileUtils;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.domain.user.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserImageFileUploadService {

    private final AwsS3Service awsS3Service;
    private final UserService userService;
    private final ImageJdbcRepository imageJdbcRepository;
    private final UserImageRepository userImageRepository;

    @Transactional
    public void upload(Long userId, List<MultipartFile> files) throws IOException {
        User user = userService.getUserById(userId);
        upload(files, user);
    }

    private void upload(List<MultipartFile> files, User user) throws IOException {
        List<UserImage> userImages = uploadImageToStorageServer(files, user);
        imageJdbcRepository.saveAllUserImage(userImages);
    }

    private List<UserImage> uploadImageToStorageServer(List<MultipartFile> files, User user)
        throws IOException {
        List<UserImage> userImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = FileUtils.getRandomFilename();
            String filepath = awsS3Service.upload(file, filename);
            userImages.add(UserImage.builder()
                .name(filename)
                .url(filepath)
                .user(user)
                .build());
        }

        return userImages;
    }

    @Transactional
    public void delete(Long userId) {
        User user = userService.getUserById(userId);
        List<UserImage> userImages = findImageListByUser(user);
        if (!userImages.isEmpty()) {
            for (UserImage userImage : userImages) {
                String path = userImage.getUrl();

                // 파일의 Url에서 55를 기준으로 문자열을 나누면 파일 키가 나옵니다.
                String filename = path.substring(55);

                // 위에서 구한 파일 키를 통해서 S3에서 해당 파일 삭제
                awsS3Service.deleteFile(filename);
            }
            userImageRepository.deleteAllByUser(user);
        }
    }

    public List<UserImage> findImageListByUser(User user) {
        return userImageRepository.findAllByUser(user);
    }

    public List<String> getImagesByUser(User user) {
        List<UserImage> userImages = findImageListByUser(user);
        List<String> imagesUrls = new ArrayList<>();
        for (UserImage userImage : userImages) {
            imagesUrls.add(userImage.getUrl());
        }
        return imagesUrls;
    }

}
