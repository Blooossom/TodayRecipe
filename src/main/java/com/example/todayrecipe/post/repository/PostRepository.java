package com.example.todayrecipe.post.repository;

import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findById(String id);


}
