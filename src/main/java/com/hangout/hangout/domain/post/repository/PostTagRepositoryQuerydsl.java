package com.hangout.hangout.domain.post.repository;

import com.hangout.hangout.domain.post.entity.Post;
import com.hangout.hangout.domain.post.entity.PostTagRel;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostTagRepositoryQuerydsl {

    List<PostTagRel> findAllByPost(Post post);

    Page<PostTagRel> findAllPostByTagName(Pageable pageable, String searchKeyword);

}
