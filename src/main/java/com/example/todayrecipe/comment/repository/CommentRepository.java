package com.example.todayrecipe.comment.repository;

import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByPostId(Post post);
    
}
