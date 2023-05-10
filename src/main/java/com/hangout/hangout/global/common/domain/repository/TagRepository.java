package com.hangout.hangout.global.common.domain.repository;

import com.hangout.hangout.global.common.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByType(String type);
}
