package com.lalala.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lalala.auth.domain.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByNickname(String nickname);

    Boolean existsByEmail(String email);
}
