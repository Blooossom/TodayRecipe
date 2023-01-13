package com.example.todayrecipe.user.repository;

import com.example.todayrecipe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUseridAndPassword(String userId, String password);

    Optional<User> findByUserid(String userId);

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByUserid(String userid);

}
