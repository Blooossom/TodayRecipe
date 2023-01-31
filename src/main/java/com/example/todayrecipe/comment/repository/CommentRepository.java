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

    List<Comment> findAllByPostId(Long post_id);
    List<Comment> findByPostId(Long post_id);
    List<Comment> findAllByUserId(Long userID);

    String deleteCommentById(Long comment_id);

    Comment findCommentById(Long comment_id);
    
}
