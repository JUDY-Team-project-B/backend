package com.hangout.hangout.domain.post.service;

import com.hangout.hangout.domain.post.PostMapper;
import com.hangout.hangout.domain.post.dto.PostSearchRequest;
import com.hangout.hangout.domain.post.repository.PostRepository;
import com.hangout.hangout.domain.post.dto.PostListResponse;
import com.hangout.hangout.domain.post.dto.PostRequest;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.PostNotFoundException;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostInfo;
import com.hangout.hangout.global.common.domain.entity.Status;
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
    public void createNewPost(PostRequest postRequest, User user) {
        Post post = mapper.toEntity(postRequest, user);
        Long newStatus = 1L;
        Status status = statusRepository.findStatusById(newStatus).orElseThrow(
                () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));
        post.getPostInfo().setStatus(status);
        postRepository.save(post);
    }
    public Post findPostById(Long postId) {
        return postRepository.findPostById(postId).orElseThrow(
                () -> new PostNotFoundException(ResponseType.POST_NOT_FOUND));
    }

    public Status findPostStatusById(Long statusId) {
        return statusRepository.findStatusById(statusId).orElseThrow(
                () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));
    }

    public List<PostListResponse> getPosts(int page, int size, PostSearchRequest postSearchRequest) {
        List<Post> posts = null;
        PageRequest pageRequest = PageRequest.of(page,size);

        if(postSearchRequest.getSearchType() == null) {
            posts = postRepository.findAllPostByCreatedAtDesc(pageRequest).getContent();
        }
        else if(postSearchRequest.getSearchType().toString().equals("title")) {
            posts = postRepository.findAllContainTitleByCreatedAtDesc(pageRequest, postSearchRequest.getSearchKeyword()).getContent();
        }
        else if(postSearchRequest.getSearchType().toString().equals("context")) {
            posts = postRepository.findAllContainContextByCreatedAtDesc(pageRequest, postSearchRequest.getSearchKeyword()).getContent();
        }
        else if(postSearchRequest.getSearchType().toString().equals("nickname")) {
            posts = postRepository.findAllContainNicknameByCreatedAtDesc(pageRequest, postSearchRequest.getSearchKeyword()).getContent();
        }
        else if(postSearchRequest.getSearchType().toString().equals("all")) {
            posts = postRepository.findAllContainTitleAndContextByCreatedAtDesc(pageRequest, postSearchRequest.getSearchKeyword()).getContent();
        }
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
        Status status = statusRepository.findStatusById(deleteStatus).orElseThrow(
                () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));
        post.getPostInfo().setStatus(status);
        postRepository.save(post);
    }
}
