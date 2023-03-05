package com.example.todayrecipe.repository;

import com.example.todayrecipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUseridAndPassword(String userId, String password);
    Optional<User> findUserByUserid(String userId);

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByUserid(String userid);

    @Modifying
    @Query(value = "UPDATE user u SET u.password = :password, u.phone = :phone, u.nickname = :nickname, u.address = :address WHERE u.email = :email", nativeQuery = true)
    Integer updateInfo(@Param("password") String password, @Param("phone") String phone, @Param("nickname") String nickname, @Param("address") String address, @Param("email") String email);
}
