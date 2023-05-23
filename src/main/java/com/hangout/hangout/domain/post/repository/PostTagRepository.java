package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostTagRel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTagRel, Long> {
    List<PostTagRel> findAllByPost(Post post);

    @Query("SELECT ptr FROM PostTagRel ptr WHERE ptr.post.postInfo.status.id = 1 AND ptr.tag.type LIKE %:searchKeyword% ORDER BY ptr.id DESC")
    List<PostTagRel> findAllPostByTagName(Pageable pageable, String searchKeyword);
}
