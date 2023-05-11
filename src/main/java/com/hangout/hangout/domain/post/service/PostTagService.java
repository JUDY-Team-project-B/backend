package com.hangout.hangout.domain.post.service;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostTagRel;
import com.hangout.hangout.domain.post.repository.PostTagRepository;
import com.hangout.hangout.global.common.domain.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostTagService {
    private final TagService tagService;

    private final PostTagRepository postTagRepository;

    public void saveTag(Post post, List<String> tagTypes) {
        if(tagTypes.size()== 0) return;

        tagTypes.stream()
                .map(tag ->
                        tagService.findByTagType(tag).orElseGet(() -> tagService.save(tag)))
                .forEach(tag -> mapTagToPost(post, tag));
    }

    private PostTagRel mapTagToPost(Post post, Tag tag) {
        return postTagRepository.save(PostTagRel.builder()
                .post(post)
                .tag(tag)
                .build());
    }

    public List<PostTagRel> findTagListByPost(Post post) {
        return postTagRepository.findAllByPost(post);
    }

    public List<String> getTagsByPost(Post post) {
        List<PostTagRel> postTagRels = findTagListByPost(post);
        List<String> tags = new ArrayList<>();
        for (PostTagRel postTagRel : postTagRels) {
            tags.add(postTagRel.getTag().getType());
        }
        return tags;
    }
}
