package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostHits;
import com.hangout.hangout.domain.user.entity.User;
import java.util.Optional;

public interface PostHitsRepositoryQuerydsl {
    Long findAllPostHits(Post post);

    Optional<PostHits> findByPostAndUser(Post post, User user);

}
