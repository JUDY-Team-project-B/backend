package com.hangout.hangout.domain.image.repository;

import com.hangout.hangout.domain.image.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository  extends JpaRepository<PostImage, Long> {
}
