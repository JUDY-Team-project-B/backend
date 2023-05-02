package com.hangout.hangout.Post.application;

import com.hangout.hangout.Post.domain.repository.PostRepository;
import com.hangout.hangout.Post.dto.PostRequest;
import com.hangout.hangout.Post.exception.PostNotFoundException;
import com.hangout.hangout.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void createNewPost(PostRequest postRequest) {
        Post post = postRequest.toEntity();
        postRepository.save(post);
    }
    public Post findPostById(Long postId) {
        return postRepository.findPostById(postId).orElseThrow(PostNotFoundException::new);
    }
}
