package com.hangout.hangout.domain.post.repository;

import static com.hangout.hangout.domain.post.entity.QPost.post;
import static com.hangout.hangout.domain.post.entity.QPostInfo.postInfo;
import static com.hangout.hangout.domain.user.entity.QUser.user;

import com.hangout.hangout.domain.post.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class PostRepositoryQuerydslImpl implements PostRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    // 게시글 ID 값을 통한 게시글 상세 조회
    @Override
    public Optional<Post> findPostById(Long postId) {
        Post post1 = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo)
            .where(
                post.id.eq(postId),
                postInfo.status.id.eq(1L)
            ).fetchOne();
        return Optional.ofNullable(post1);
    }

    // 검색 조건 없는 모든 게시글 조회
    @Override
    public Page<Post> findAllPostByCreatedAtDesc(Pageable pageable) {

        List<Post> postList = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo)
            .where(postInfo.status.id.eq(1L))
            .orderBy(post.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .join(post.postInfo, postInfo)
            .where(postInfo.status.id.eq(1L));

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    // 검색 조건 all(제목,내용) 인 모든 게시글 조회
    @Override
    public Page<Post> findAllContainTitleAndContextByCreatedAtDesc(Pageable pageable,
        String searchKeyword) {

        BooleanExpression titleOrContextContainsKeyword = post.title.containsIgnoreCase(
                searchKeyword)
            .or(post.context.containsIgnoreCase(searchKeyword));

        List<Post> postList = postListKeyword(pageable, titleOrContextContainsKeyword);

        JPAQuery<Long> countQuery = countQueryMethod(titleOrContextContainsKeyword);

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    // 검색 조건 title(제목) 인 모든 게시글 조회
    @Override
    public Page<Post> findAllContainTitleByCreatedAtDesc(Pageable pageable, String searchKeyword) {
        BooleanExpression titleContainsKeyword = post.title.containsIgnoreCase(searchKeyword);

        List<Post> postList = postListKeyword(pageable, titleContainsKeyword);

        JPAQuery<Long> countQuery = countQueryMethod(titleContainsKeyword);

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    // 검색 조건 context(내용) 인 모든 게시글 조회
    @Override
    public Page<Post> findAllContainContextByCreatedAtDesc(Pageable pageable,
        String searchKeyword) {
        BooleanExpression ContextContainsKeyword = post.context.containsIgnoreCase(searchKeyword);

        List<Post> postList = postListKeyword(pageable, ContextContainsKeyword);

        JPAQuery<Long> countQuery = countQueryMethod(ContextContainsKeyword);

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    // 98 ~ 123 : 검색 조건(내용, 제목, all) 일 때 중복되는 코드를 함수로 묶어서 사용
    public List<Post> postListKeyword(Pageable pageable, BooleanExpression booleanExpression) {
        List<Post> postList = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo)
            .where(
                postInfo.status.id.eq(1L),
                booleanExpression
            )
            .orderBy(post.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        return postList;
    }

    public JPAQuery<Long> countQueryMethod(BooleanExpression booleanExpression) {
        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .join(post.postInfo, postInfo)
            .where(
                postInfo.status.id.eq(1L),
                booleanExpression
            );
        return countQuery;
    }

    // 검색 조건 nickname(닉네임) 인 모든 게시글 조회
    @Override
    public Page<Post> findAllContainNicknameByCreatedAtDesc(Pageable pageable,
        String searchKeyword) {
        BooleanExpression nicknameContainsKeyword = user.nickname.likeIgnoreCase(
            "%" + searchKeyword + "%");

        List<Post> postList = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo)
            .join(post.user, user)
            .where(
                postInfo.status.id.eq(1L),
                nicknameContainsKeyword
            )
            .orderBy(post.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .join(post.postInfo, postInfo)
            .join(post.user, user)
            .where(
                postInfo.status.id.eq(1L),
                nicknameContainsKeyword
            );

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }
}
