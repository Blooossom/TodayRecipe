package com.example.todayrecipe.repository;

import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostno(Long postNo);

    Integer deleteByPostno(Long postNo);

    List<Post> findAllByOrderByRecommendDesc();

    @Modifying
    @Query("update Post post set post.view = post.view + 1 where post.postno = :postno")
    int updateView(Long postno);

    List<Post> findAllByUser(User user);

    @Modifying
    @Query("update Post post set post.recommend = post.recommend + 1 where post.postno = :postNo")
    int updateRecommend(Long postNo);

//    @Modifying
//    @Query(value = "UPDATE post p SET p.title =: title, p.content =:content, p.comment WHERE p.postNo =:postNo", nativeQuery = true)
//    Long updatePost(@Param("title") String title, @Param("content") String contnt, @Param("postNo") Long postNo);

}
