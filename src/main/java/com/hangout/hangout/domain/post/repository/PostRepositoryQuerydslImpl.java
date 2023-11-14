package com.hangout.hangout.domain.post.repository;

import static com.hangout.hangout.domain.like.entity.QPostLike.postLike;
import static com.hangout.hangout.domain.post.entity.QPost.post;
import static com.hangout.hangout.domain.post.entity.QPostHits.postHits;
import static com.hangout.hangout.domain.post.entity.QPostInfo.postInfo;
import static com.hangout.hangout.domain.user.entity.QUser.user;

import com.hangout.hangout.domain.post.dto.PostSearchRequest;
import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class PostRepositoryQuerydslImpl implements PostRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    // 게시글 ID 값을 통한 게시글 상세 조회
    @Override
    public Optional<Post> findPostById(Long postId) {
        Post post1 = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
            .where(
                post.id.eq(postId),
                postInfo.status.id.eq(1L)
            ).fetchOne();
        return Optional.ofNullable(post1);
    }

    /**
     * 현재 로그인 중인 유저가 작성한 게시물 조회
     *
     * @param pageable    pagination의 offset과 limit정보 전달을 위한 Pageable 객체
     * @param currentUser 현재 로그인 중인 유저
     * @return Page<Post>
     */
    @Override
    public Page<Post> findAllPostByUser(Pageable pageable, User currentUser) {

        List<Post> postList = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
            .where(
                postInfo.status.id.eq(1L),
                post.user.eq(currentUser)
            )
            .orderBy(post.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
            .where(
                postInfo.status.id.eq(1L),
                post.user.eq(currentUser)
            );

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    /**
     * 현재 유저가 좋아요를 누른 게시물 조회
     *
     * @param pageable    pagination의 offset과 limit정보 전달을 위한 Pageable 객체
     * @param currentUser 현재 로그인 중인 유저
     * @return Page<Post>
     */
    @Override
    public Page<Post> findAllPostByUserLike(Pageable pageable, User currentUser) {

        List<Post> postList = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
            .join(post.postLikes, postLike)
            .where(
                postInfo.status.id.eq(1L),
                postLike.user.eq(currentUser)
            )
            .orderBy(post.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
            .join(post.postLikes, postLike)
            .where(
                postInfo.status.id.eq(1L),
                postLike.user.eq(currentUser)
            );

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    // 검색 조건 없는 모든 게시글 조회
    @Override
    public Page<Post> findAllPostByCreatedAtDesc(Pageable pageable) {

        List<Post> postList = queryFactory
                .selectFrom(post)
                .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
                .where(postInfo.status.id.eq(1l))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
                .where(postInfo.status.id.eq(1L));

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    // 검색 조건 있는 모든 게시글 조회
    @Override
    public Page<Post> SearchAllPostByCreatedAtDesc(Pageable pageable, PostSearchRequest postSearchRequest) {

        List<Post> postList = queryFactory
            .selectFrom(post)
            .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
            .where(
                    postInfo.status.id.eq(1L),
                    searchByBuilder(postSearchRequest)
            )
            .orderBy(post.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .join(post.postInfo, postInfo).fetchJoin()
                .join(post.user, user).fetchJoin()
            .where(
                    postInfo.status.id.eq(1L),
                    searchByBuilder(postSearchRequest)
            );

        return PageableExecutionUtils.getPage(postList, pageable, countQuery::fetchOne);
    }

    private BooleanBuilder searchByBuilder(PostSearchRequest postSearchRequest) {
        BooleanBuilder builder = new BooleanBuilder();

        // 검색 타입이 있으나 키워드 2번째가 없는 경우
        if (StringUtils.hasText(postSearchRequest.getSearchType().toString())
                && !StringUtils.hasText(postSearchRequest.getSearchKeyword2())) {
            if (postSearchRequest.getSearchType().toString().equals("title")){
                builder.and(post.title.contains(postSearchRequest.getSearchKeyword1()));
            }
            if (postSearchRequest.getSearchType().toString().equals("context")){
                builder.and(post.context.contains(postSearchRequest.getSearchKeyword1()));
            }
            if (postSearchRequest.getSearchType().toString().equals("nickname")){
                builder.and(post.user.nickname.eq(postSearchRequest.getSearchKeyword1()));
            }
            if (postSearchRequest.getSearchType().toString().equals("all")){
                builder.and(post.title.contains(postSearchRequest.getSearchKeyword1())
                        .or(post.context.contains(postSearchRequest.getSearchKeyword1())));
            }
            if (postSearchRequest.getSearchType().toString().equals("state")){
                builder.and(post.postInfo.map.state.contains(postSearchRequest.getSearchKeyword1()));
            }
            if (postSearchRequest.getSearchType().toString().equals("city")){
                builder.and(post.postInfo.map.city.contains(postSearchRequest.getSearchKeyword1()));
            }
        }
        // 검색 타입이 도시,지역 둘 다 검색 , 키워드 2번째가 있는 경우
        else if (StringUtils.hasText(postSearchRequest.getSearchType().toString())
                && StringUtils.hasText(postSearchRequest.getSearchKeyword2())){
            if (postSearchRequest.getSearchType().toString().equals("stateAndCity")){
                builder.and(post.postInfo.map.state.contains(postSearchRequest.getSearchKeyword1())
                        .and(post.postInfo.map.city.contains(postSearchRequest.getSearchKeyword2())));
            }
        }

        return builder;
    }

    @Override
    public void addLikeCount(Post selectpost) {
        queryFactory.update(post)
            .set(post.likeCount, post.likeCount.add(1))
            .where(post.eq(selectpost))
            .execute();
    }

    @Override
    public void subLikeCount(Post selectpost) {
        queryFactory.update(post)
            .set(post.likeCount, post.likeCount.subtract(1))
            .where(post.eq(selectpost))
            .execute();
    }

    /**
     * 조회수에 따라 정렬된 게시물 조회
     *
     * @param page         pagination의 offset과 limit정보 전달을 위한 Pageable 객체
     * @param isDescending false인 경우 오름차순, true인 경우 내림차순 조회
     * @return Page<Post>
     */
    @Override
    public Page<Post> findAllByOrderByPostHits(Pageable page, boolean isDescending) {

        JPAQuery<Post> postJPAQuery = queryFactory.selectFrom(post)
            .innerJoin(postHits)
            .on(post.eq(postHits.post))
            .groupBy(postHits.post)
            .offset(page.getOffset())
            .limit(page.getPageSize());

        if (isDescending) {
            postJPAQuery.orderBy(postHits.viewCnt.sum().desc());
        } else {
            postJPAQuery.orderBy(postHits.viewCnt.sum().asc());
        }

        JPAQuery<Long> count = queryFactory.select(post.countDistinct())
            .from(post)
            .innerJoin(postHits)
            .on(post.eq(postHits.post));

        List<Post> posts = postJPAQuery.fetch();
        return PageableExecutionUtils.getPage(posts, page, count::fetchOne);
    }

    /**
     * 좋아요 수에 따라 정렬된 게시물 조회
     *
     * @param page         pagination의 offset과 limit정보 전달을 위한 Pageable 객체
     * @param isDescending false인 경우 오름차순, true인 경우 내림차순 조회
     * @return Page<Post>
     */
    @Override
    public Page<Post> findAllByOrderByPostLikes(Pageable page, boolean isDescending) {
        JPAQuery<Post> postJPAQuery = queryFactory.selectFrom(post)
            .groupBy(post)
            .offset(page.getOffset())
            .limit(page.getPageSize());

        if (isDescending) {
            postJPAQuery.orderBy(post.likeCount.sum().desc(), post.id.asc());
        } else {
            postJPAQuery.orderBy(post.likeCount.sum().asc(), post.id.asc());
        }

        JPAQuery<Long> count = queryFactory.select(post.count())
            .from(post);

        List<Post> posts = postJPAQuery.fetch();
        return PageableExecutionUtils.getPage(posts, page, count::fetchOne);
    }


}
