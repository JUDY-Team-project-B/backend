package com.hangout.hangout.domain.post.service;

import com.hangout.hangout.domain.like.entity.PostLike;
import com.hangout.hangout.domain.like.repository.LikeRepository;
import com.hangout.hangout.domain.post.PostMapper;
import com.hangout.hangout.domain.post.dto.PostListResponse;
import com.hangout.hangout.domain.post.dto.PostRequest;
import com.hangout.hangout.domain.post.dto.PostSearchRequest;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostHits;
import com.hangout.hangout.domain.post.entity.PostHitsRedis;
import com.hangout.hangout.domain.post.entity.PostInfo;
import com.hangout.hangout.domain.post.entity.PostTagRel;
import com.hangout.hangout.domain.post.repository.PostHitsRedisRepository;
import com.hangout.hangout.domain.post.repository.PostHitsRepository;
import com.hangout.hangout.domain.post.repository.PostRepository;
import com.hangout.hangout.domain.user.entity.User;
import com.hangout.hangout.global.common.domain.entity.Status;
import com.hangout.hangout.global.common.domain.repository.StatusRepository;
import com.hangout.hangout.global.error.ResponseType;
import com.hangout.hangout.global.exception.PostNotFoundException;
import com.hangout.hangout.global.exception.StatusNotFoundException;
import com.hangout.hangout.global.exception.UnAuthorizedAccessException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final StatusRepository statusRepository;
    private final PostHitsRedisRepository postHitsRedisRepository;
    private final PostHitsRepository postHitsRepository;
    private final LikeRepository likeRepository;
    private final PostTagService postTagService;
    private final PostMapper mapper;


    @Transactional
    public void createNewPost(PostRequest postRequest, User user) {
        Post post = mapper.toEntity(postRequest, user);

        Long newStatus = 1L;
        Status status = statusRepository.findStatusById(newStatus).orElseThrow(
            () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));
        post.getPostInfo().setStatus(status);

        postTagService.saveTag(post, postRequest.getTags());

        postRepository.save(post);
    }

    public Post findPostById(Long postId) {
        return postRepository.findPostById(postId).orElseThrow(
            () -> new PostNotFoundException(ResponseType.POST_NOT_FOUND));
    }

    public int findLike(User user, Long postId) {
        Post post = postRepository.findPostById(postId)
            .orElseThrow(() -> new PostNotFoundException(ResponseType.POST_NOT_FOUND));
        // 좋아요 상태가 아니면 0, 맞다면 1
        Optional<PostLike> findLike = likeRepository.findByUserAndPost(user, post);

        if (findLike.isEmpty()) {
            return 0;
        } else {
            return 1;
        }

    }

    public Status findPostStatusById(Long statusId) {
        return statusRepository.findStatusById(statusId).orElseThrow(
            () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));
    }

    public List<PostListResponse> getPosts(int page, int size,
        PostSearchRequest postSearchRequest) {
        List<Post> posts = null;
        PageRequest pageRequest = PageRequest.of(page, size);

        if (postSearchRequest.getSearchType() == null) {
            posts = postRepository.findAllPostByCreatedAtDesc(pageRequest).getContent();
        } else if (postSearchRequest.getSearchType().toString().equals("title")) {
            posts = postRepository.findAllContainTitleByCreatedAtDesc(pageRequest,
                postSearchRequest.getSearchKeyword()).getContent();
        } else if (postSearchRequest.getSearchType().toString().equals("context")) {
            posts = postRepository.findAllContainContextByCreatedAtDesc(pageRequest,
                postSearchRequest.getSearchKeyword()).getContent();
        } else if (postSearchRequest.getSearchType().toString().equals("tag")) {
            posts = postTagService.findAllPostByTag(pageRequest,
                postSearchRequest.getSearchKeyword());
        } else if (postSearchRequest.getSearchType().toString().equals("nickname")) {
            posts = postRepository.findAllContainNicknameByCreatedAtDesc(pageRequest,
                postSearchRequest.getSearchKeyword()).getContent();
        } else if (postSearchRequest.getSearchType().toString().equals("all")) {
            posts = postRepository.findAllContainTitleAndContextByCreatedAtDesc(pageRequest,
                postSearchRequest.getSearchKeyword()).getContent();
        }
        return mapper.toDtoList(posts);
    }

    @Transactional
    public void updatePost(Post post, PostInfo postInfo, PostRequest postRequest, User user) {
        // 로그인한 유저와 수정을 한 유저가 같은지 검증하는 로직
        if (isMatchedNickname(post, user)) {
            // 태그 업데이트
            for (PostTagRel postTagRel : postTagService.findTagListByPost(post)) {
                postTagRel.setPost(null);
                postTagRel.setTag(null);
            }
            postTagService.saveTag(post, postRequest.getTags());
            post.updatePost(postRequest);
            postInfo.updatePostInfo(postRequest);
        }
    }

    @Transactional
    public void deletePost(Post post, User user) {
        // 로그인한 유저와 수정을 한 유저가 같은지 검증하는 로직
        if (isMatchedNickname(post, user)) {
            // postID에 해당하는 중간테이블의 값을 null로 설정
            for (PostTagRel postTagRel : postTagService.findTagListByPost(post)) {
                postTagRel.setPost(null);
                postTagRel.setTag(null);
            }

            Long deleteStatus = 2L;
            Status status = statusRepository.findStatusById(deleteStatus).orElseThrow(
                () -> new StatusNotFoundException(ResponseType.STATUS_NOT_FOUND));
            post.getPostInfo().setStatus(status);
            postRepository.save(post);
        }
    }

    public boolean isMatchedNickname(Post post, User user) {
        String userNickname = user.getNickname();

        if (!post.getUser().getNickname().equals(userNickname)) {
            throw new UnAuthorizedAccessException(ResponseType.REQUEST_NOT_VALID);
        }
        return true;
    }

    /**
     * 특정 post의 조회수를 가져옴
     *
     * @param postId post id
     * @return int
     */
    public Long getPostHits(Long postId) {
        Post post = postRepository.findPostById(postId).orElseThrow(
            () -> new PostNotFoundException(ResponseType.POST_NOT_FOUND)
        );

        return postHitsRepository.findAllPostHits(post);
    }

    @Transactional
    public void updatePostHits(Long postId, User user) {
        Post post = postRepository.findPostById(postId)
            .orElseThrow(() -> new PostNotFoundException(ResponseType.POST_NOT_FOUND));
        Optional<PostHitsRedis> byId = postHitsRedisRepository.findById(
            post.getId() + "-" + user.getId());

        if (byId.isEmpty()) {
            postHitsRedisRepository.save(PostHitsRedis.builder()
                .id(postId + "-" + user.getId())
                .build());
            updatePostHitsViewCount(post, user);
        }
    }

    @Transactional
    public void updatePostHitsViewCount(Post post, User user) {
        Optional<PostHits> byPostAndUser = postHitsRepository.findByPostAndUser(post, user);
        if (byPostAndUser.isEmpty()) {
            postHitsRepository.save(PostHits.builder()
                .post(post)
                .user(user)
                .viewCnt(1)
                .build());
        } else {
            PostHits postHits = byPostAndUser.get();
            postHits.updateViewCount(postHits.getViewCnt() + 1);
        }
    }

    public List<PostListResponse> getPostHitsFiltering(Integer page, Integer size,
        boolean isDescending) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Post> posts = postRepository.findAllByOrderByPostHits(pageRequest,
            isDescending).getContent();
        return mapper.toDtoList(posts);
    }

    public List<PostListResponse> getPostLikesFiltering(Integer page, Integer size,
        boolean isDescending) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Post> posts = postRepository.findAllByOrderByPostLikes(pageRequest,
            isDescending).getContent();
        return mapper.toDtoList(posts);
    }
}
