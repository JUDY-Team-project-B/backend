package com.hangout.hangout.domain.like.repository;

import static com.hangout.hangout.domain.like.entity.QCommentLike.commentLike;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.like.entity.CommentLike;
import com.hangout.hangout.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class LikeCommentRepositoryQuerydslImpl implements LikeCommentRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<CommentLike> findByUserAndComment(User user, Comment comment) {
        CommentLike commentLike1 = queryFactory
                .selectFrom(commentLike)
                .where(
                        commentLike.user.eq(user),
                        commentLike.comment.eq(comment)
                ).fetchOne();
        return Optional.ofNullable(commentLike1);
    }

    @Override
    public void deleteCommentLikeByUserAndComment(User user, Comment comment) {
        queryFactory.delete(commentLike)
                .where(
                        commentLike.user.eq(user),
                        commentLike.comment.eq(comment)
                ).execute();
    }
}
