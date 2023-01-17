package com.example.todayrecipe.post.repository;

import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long post_id);

    String deletePostById(Long post_id);

    @Modifying
    @Query("update Post post set post.view = post.view + 1 where post.id = :id")
    int updateView(Long id);




}
