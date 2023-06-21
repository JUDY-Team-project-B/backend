package com.hangout.hangout.domain.image.repository;

import com.hangout.hangout.domain.image.entity.PostImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String BULK_INSERT_SQL = "INSERT INTO " +
            "`postimage`(`post_image_name` , `post_image_url`, `created_At`, `updated_At`, `post_id`, `is_removed`) " +
            "VALUES(?, ?, ?, ?, ?, ?)";

    public void saveAll(List<PostImage> postImages) {
        jdbcTemplate.batchUpdate(BULK_INSERT_SQL,
                postImages,
                postImages.size(),
                (ps, postImage) -> {
                    LocalDateTime now = LocalDateTime.now();
                    ps.setString(1, postImage.getName());
                    ps.setString(2, postImage.getUrl());
                    ps.setTimestamp(3, Timestamp.valueOf(now));
                    ps.setTimestamp(4, Timestamp.valueOf(now));
                    ps.setLong(5, postImage.getPost().getId());
                    ps.setBoolean(6, postImage.isRemoved());
                });
    }
}

