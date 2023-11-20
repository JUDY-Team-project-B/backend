package com.hangout.hangout.domain.comment.domain.repository;

import static com.hangout.hangout.domain.comment.entity.QComment.comment;
import static com.hangout.hangout.domain.user.entity.QUser.user;

import com.hangout.hangout.domain.comment.entity.Comment;
import com.hangout.hangout.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryQuerydslImpl implements CommentRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findByPostId(Long postId) {
        JPAQuery<Comment> query = queryFactory
            .select(comment)
            .from(comment)
            .where(
                comment.post.id.eq(postId),
                comment.status.id.eq(1L)
            );
        return query.fetch();
    }

    @Override
    public Optional<Comment> findCommentById(Long commentId) {
        Comment comment1 = queryFactory
            .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
            .where(
                comment.id.eq(commentId),
                comment.status.id.eq(1L)
            ).fetchOne();
        return Optional.ofNullable(comment1);
    }

    @Override
    public List<Comment> findCommentByUser(User cnUser) {
        JPAQuery<Comment> query = queryFactory
            .selectFrom(comment)
                .join(comment.user, user).fetchJoin()
            .where(
                comment.user.eq(cnUser),
                comment.status.id.eq(1L)
            )
            .orderBy(comment.id.desc());
        return query.fetch();
    }

    @Override
    public void addLikeCount(Comment selectcomment) {
        queryFactory.update(comment)
            .set(comment.likeCount, comment.likeCount.add(1))
            .where(comment.eq(selectcomment))
            .execute();
    }

    @Override
    public void subLikeCount(Comment selectcomment) {
        queryFactory.update(comment)
            .set(comment.likeCount, comment.likeCount.subtract(1))
            .where(comment.eq(selectcomment))
            .execute();
    }
}
