package com.hangout.hangout.domain.post.service;

import com.hangout.hangout.global.common.domain.entity.Tag;
import com.hangout.hangout.global.common.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Optional<Tag> findByTagType(String type) {
        return tagRepository.findByType(type);
    }

    public Tag save(String type) {
        return tagRepository.save(
                Tag.builder()
                        .type(type)
                        .build());
    }
}
