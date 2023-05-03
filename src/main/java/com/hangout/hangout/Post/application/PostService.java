package com.hangout.hangout.Post.application;

import com.hangout.hangout.Post.PostMapper;
import com.hangout.hangout.Post.domain.repository.PostRepository;
import com.hangout.hangout.Post.dto.PostListResponse;
import com.hangout.hangout.Post.dto.PostRequest;
import com.hangout.hangout.Post.exception.PostNotFoundException;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostInfo;
import com.hangout.hangout.global.common.domain.Status;
import com.hangout.hangout.global.common.domain.repository.StatusRepository;
import com.hangout.hangout.global.exception.StatusNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final StatusRepository statusRepository;

    private final PostMapper mapper;

    @Transactional
    public void createNewPost(PostRequest postRequest) {
        Post post = mapper.toEntity(postRequest);
        Long newStatus = 1L;
        Status status = statusRepository.findStatusById(newStatus).orElseThrow(StatusNotFoundException::new);
        post.getPostInfo().setStatus(status);
        postRepository.save(post);
    }
    public Post findPostById(Long postId) {
        return postRepository.findPostById(postId).orElseThrow(PostNotFoundException::new);
    }

    public Status findPostStatusById(Long statusId) {
        return statusRepository.findStatusById(statusId).orElseThrow(StatusNotFoundException::new);
    }

    public List<PostListResponse> getPosts(PageRequest pageRequest) {
        List<Post> posts = postRepository.findAll(pageRequest).getContent();
        return mapper.toDtoList(posts);
    }

    @Transactional
    public void updatePost(Post post, PostInfo postInfo, PostRequest postRequest) {
        // 태그 업데이트 기능은 추가 예정
        // 유저 기능 완료 후 로그인한 유저와 수정을 한 유저가 같은지 검증하는 로직 구현 예정
        post.updatePost(postRequest);
        postInfo.updatePostInfo(postRequest);
    }
    @Transactional
    public void deletePost(Post post) {
        // 유저 기능 완료 후 로그인한 유저와 수정을 한 유저가 같은지 검증하는 로직 구현 예정
        Long deleteStatus = 2L;
        Status status = statusRepository.findStatusById(deleteStatus).orElseThrow(StatusNotFoundException::new);
        post.getPostInfo().setStatus(status);
        postRepository.save(post);
    }
}
