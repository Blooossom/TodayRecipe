package com.example.todayrecipe.admin.repository;

import com.example.todayrecipe.admin.entity.Admin;
import com.example.todayrecipe.user.dto.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {




}
