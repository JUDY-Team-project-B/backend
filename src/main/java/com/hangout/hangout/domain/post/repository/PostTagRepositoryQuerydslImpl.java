package com.hangout.hangout.domain.post.repository;

import static com.hangout.hangout.domain.post.entity.QPost.post;
import static com.hangout.hangout.domain.post.entity.QPostInfo.postInfo;
import static com.hangout.hangout.domain.post.entity.QPostTagRel.postTagRel;
import static com.hangout.hangout.global.common.domain.entity.QTag.tag;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostTagRel;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class PostTagRepositoryQuerydslImpl implements PostTagRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostTagRel> findAllByPost(Post posts) {
        return queryFactory
            .selectFrom(postTagRel)
            .where(postTagRel.post.eq(posts))
            .fetch();
    }

    @Override
    public Page<PostTagRel> findAllPostByTagName(Pageable pageable, String searchKeyword) {
        BooleanExpression tagNameContainsKeyword = postTagRel.tag.type.likeIgnoreCase(
            "%" + searchKeyword + "%");

        List<PostTagRel> postTagRels = queryFactory
            .selectFrom(postTagRel)
            .join(postTagRel.post, post).fetchJoin()
            .join(post.postInfo, postInfo).fetchJoin()
            .join(postTagRel.tag, tag).fetchJoin()
            .where(
                postInfo.status.id.eq(1L),
                tagNameContainsKeyword
            )
            .orderBy(postTagRel.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(postTagRel.count())
            .from(postTagRel)
            .join(postTagRel.post, post).fetchJoin()
            .join(post.postInfo, postInfo).fetchJoin()
            .join(postTagRel.tag, tag).fetchJoin()
            .where(
                postInfo.status.id.eq(1L),
                tagNameContainsKeyword
            );

        return PageableExecutionUtils.getPage(postTagRels, pageable, countQuery::fetchOne);
    }
}
