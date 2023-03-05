package com.example.todayrecipe.repository;

import com.example.todayrecipe.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long post_id);

    String deletePostById(Long post_id);

    List<Post> findAllByOrderByRecommendDesc();

    @Modifying
    @Query("update Post post set post.view = post.view + 1 where post.id = :id")
    int updateView(Long id);

    List<Post> findAllByUserId(Long userid);

    @Modifying
    @Query("update Post post set post.recommend = post.recommend + 1 where post.id = :id")
    int updateRecommend(Long id);

}
