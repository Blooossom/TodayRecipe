package com.example.todayrecipe.repository;

import com.example.todayrecipe.dto.comment.UpdateCommentReqDTO;
import com.example.todayrecipe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long post_id);
    List<Comment> findByPostId(Long post_id);
    List<Comment> findAllByUserId(Long userID);

    String deleteCommentById(Long comment_id);

    Comment findByCommentNo(Long commentNo);

    @Modifying
    @Query(value = "UPDATE comment c SET c.content =:content WHERE c.commentNo =:commentNo", nativeQuery = true)
    Integer updateComment(@Param("content") String content, @Param("commentNo") Long commentNo);
    
}
