package com.hangout.hangout.domain.like.repository;

import com.hangout.hangout.domain.like.entity.PostLike;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import java.util.Optional;

public interface LikeRepositoryQuerydsl {

    Optional<PostLike> findByUserAndPost(User user, Post post);

    void deletePostLikeByUserAndPost(User user, Post post);
}
