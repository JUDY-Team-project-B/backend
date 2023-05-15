package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 ID 값을 통한 게시글 상세 조회
    @Query("SELECT p FROM Post p WHERE p.id = :postId AND p.postInfo.status.id = 1")
    Optional<Post> findPostById(Long postId);

    // 검색 조건 없는 모든 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.postInfo.status.id = 1 ORDER BY p.id DESC")
    Page<Post> findAllPostByCreatedAtDesc(Pageable pageable);

    // 검색 조건 all(제목,내용) 인 모든 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.postInfo.status.id = 1 AND (p.title LIKE %:searchKeyword% OR p.context LIKE %:searchKeyword%) ORDER BY p.id DESC")
    Page<Post> findAllContainTitleAndContextByCreatedAtDesc(Pageable pageable, @Param("searchKeyword") String searchKeyword);

    // 검색 조건 title(제목) 인 모든 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.postInfo.status.id = 1 AND (p.title LIKE %:searchKeyword%) ORDER BY p.id DESC")
    Page<Post> findAllContainTitleByCreatedAtDesc(Pageable pageable, @Param("searchKeyword") String searchKeyword);

    // 검색 조건 context(내용) 인 모든 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.postInfo.status.id = 1 AND (p.context LIKE %:searchKeyword%) ORDER BY p.id DESC")
    Page<Post> findAllContainContextByCreatedAtDesc(Pageable pageable, @Param("searchKeyword") String searchKeyword);

    // 검색 조건 nickname(닉네임) 인 모든 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.postInfo.status.id = 1 AND (p.user.nickname LIKE %:searchKeyword%) ORDER BY p.id DESC")
    Page<Post> findAllContainNicknameByCreatedAtDesc(Pageable pageable, @Param("searchKeyword") String searchKeyword);
}
