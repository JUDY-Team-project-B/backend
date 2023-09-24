package com.hangout.hangout.domain.image.repository;

import com.hangout.hangout.domain.image.entity.UserImage;
import com.hangout.hangout.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    @Query("SELECT ui FROM UserImage ui WHERE ui.user = :user")
    List<UserImage> findAllByUser(User user);

    void deleteAllByUser(User user);
}
