package com.hangout.hangout.global.common.domain.repository;

import com.hangout.hangout.global.common.domain.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByType(String type);
}
