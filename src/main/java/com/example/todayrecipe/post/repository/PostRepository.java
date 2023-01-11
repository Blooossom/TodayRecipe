package com.example.todayrecipe.post.repository;

import com.example.todayrecipe.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long post_id);

    String deletePostById(Long post_id);


}
