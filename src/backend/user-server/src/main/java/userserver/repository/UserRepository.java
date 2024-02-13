package userserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import userserver.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
