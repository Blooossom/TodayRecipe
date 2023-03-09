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

    Optional<Post> findByPostNo(Long postNo);

    Integer deleteByPostNo(Long postNo);

    List<Post> findAllByOrderByRecommendDesc();

    @Modifying
    @Query("update Post post set post.view = post.view + 1 where post.id = :id")
    int updateView(Long id);

    List<Post> findAllByUser(User user);

    @Modifying
    @Query("update Post post set post.recommend = post.recommend + 1 where post.id = :id")
    int updateRecommend(Long id);

    @Modifying
    @Query(value = "UPDATE post p SET p.content =:content WHERE p.postNo =:postNo", nativeQuery = true)
    Integer updatePost(@Param("title") String title, @Param("content") String contnt, @Param("postNo") Long postNo);

}
