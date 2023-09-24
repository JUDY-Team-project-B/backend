package com.hangout.hangout.domain.image.service;

import com.hangout.hangout.domain.image.entity.PostImage;
import com.hangout.hangout.domain.image.repository.ImageJdbcRepository;
import com.hangout.hangout.domain.image.repository.PostImageRepository;
import com.hangout.hangout.domain.image.util.FileUtils;
import com.hangout.hangout.domain.post.entity.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageFileUploadService {

    private final AwsS3Service awsS3Service;
    private final ImageJdbcRepository imageJdbcRepository;
    private final PostImageRepository postImageRepository;

    @Transactional
    public void upload(List<MultipartFile> files, Post post) throws IOException {
        List<PostImage> postImages = uploadImageToStorageServer(files, post);
        imageJdbcRepository.saveAllPostImage(postImages);
    }

    private List<PostImage> uploadImageToStorageServer(List<MultipartFile> files, Post post)
        throws IOException {
        List<PostImage> postImages = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = FileUtils.getRandomFilename();
            String filepath = awsS3Service.upload(file, filename);
            postImages.add(PostImage.builder()
                .name(filename)
                .url(filepath)
                .post(post)
                .build());
        }

        return postImages;
    }

    @Transactional
    public void delete(Post post) {
        List<PostImage> postImages = findImageListByPost(post);
        if (!postImages.isEmpty()) {
            for (PostImage postImage : postImages) {
                String path = postImage.getUrl();

                // 파일의 Url에서 55를 기준으로 문자열을 나누면 파일 키가 나옵니다.
                String filename = path.substring(55);

                // 위에서 구한 파일 키를 통해서 S3에서 해당 파일 삭제
                awsS3Service.deleteFile(filename);
            }
            postImageRepository.deleteAllByPost(post);
        }
    }

    public List<PostImage> findImageListByPost(Post post) {
        return postImageRepository.findAllByPost(post);
    }

    public List<String> getImagesByPost(Post post) {
        List<PostImage> postImages = findImageListByPost(post);
        List<String> imagesUrls = new ArrayList<>();
        for (PostImage postImage : postImages) {
            imagesUrls.add(postImage.getUrl());
        }
        return imagesUrls;
    }

}
