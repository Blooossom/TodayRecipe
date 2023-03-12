package com.example.todayrecipe.repository;

import com.example.todayrecipe.dto.comment.UpdateCommentReqDTO;
import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);

    Integer deleteByCommentNo(Long commentNo);

    Comment findByCommentNo(Long commentNo);

    @Modifying
    @Query(value = "UPDATE comment c SET c.content =:content WHERE c.commentno =:commentNo", nativeQuery = true)
    Integer updateComment(@Param("content") String content, @Param("commentNo") Long commentNo);
    
}
