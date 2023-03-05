package com.example.todayrecipe.repository;

import com.example.todayrecipe.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Blacklist, Long> {

    boolean existsByToken(String token);
}
