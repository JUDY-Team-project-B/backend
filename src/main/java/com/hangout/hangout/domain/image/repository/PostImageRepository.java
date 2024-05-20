package com.hangout.hangout.domain.image.repository;

import com.hangout.hangout.domain.image.entity.PostImage;
import com.hangout.hangout.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Query("SELECT pi FROM PostImage pi WHERE pi.post = :post")
    List<PostImage> findAllByPost(Post post);

    void deleteAllByPost(Post post);
}
