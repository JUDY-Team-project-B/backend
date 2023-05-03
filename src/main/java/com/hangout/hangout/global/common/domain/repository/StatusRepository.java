package com.hangout.hangout.global.common.domain.repository;

import com.hangout.hangout.global.common.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findStatusById(Long statusId);
}
