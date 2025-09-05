package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.User;
import com.example.jobtracker_api.model.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
