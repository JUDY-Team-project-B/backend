package com.hangout.hangout.domain.auth.repository;

import com.hangout.hangout.domain.auth.entity.Token;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND NOT (t.expired = true OR t.revoked = true)")
    List<Token> findAllValidTokensByUserId(@Param("userId") Long userId);

    Optional<Token> findByToken(String token);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.expired = true OR t.revoked = true")
    void deleteExpiredAndRevokedTokens();
}
