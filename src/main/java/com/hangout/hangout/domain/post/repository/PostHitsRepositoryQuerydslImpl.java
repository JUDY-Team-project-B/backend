package com.hangout.hangout.domain.post.repository;

import static com.hangout.hangout.domain.post.entity.QPostHits.postHits;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostHits;
import com.hangout.hangout.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostHitsRepositoryQuerydslImpl implements PostHitsRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findAllPostHits(Post selectPost) {
        return queryFactory.select(postHits.viewCnt.count())
            .from(postHits)
            .where(postHits.post.eq(selectPost)).fetchFirst();
    }

    @Override
    public Optional<PostHits> findByPostAndUser(Post post, User user) {
        return Optional.ofNullable(queryFactory.select(postHits)
            .from(postHits)
            .where(postHits.post.eq(post), postHits.user.eq(user)).fetchFirst());
    }

}
