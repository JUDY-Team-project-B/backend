package com.hangout.hangout.domain.like.repository;

import static com.hangout.hangout.domain.like.entity.QPostLike.postLike;

import com.hangout.hangout.domain.like.entity.PostLike;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LikeRepositoryQuerydslImpl implements LikeRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostLike> findByUserAndPost(User user, Post post) {
        PostLike postLike1 = queryFactory
                .selectFrom(postLike)
                .where(
                        postLike.user.eq(user),
                        postLike.post.eq(post)
                ).fetchOne();
        return Optional.ofNullable(postLike1);
    }

    @Override
    public void deletePostLikeByUserAndPost(User user, Post post) {
        queryFactory.delete(postLike)
                .where(
                        postLike.user.eq(user),
                        postLike.post.eq(post)
                ).execute();
    }
}
