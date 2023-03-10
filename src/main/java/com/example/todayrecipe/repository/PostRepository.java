package com.example.todayrecipe.repository;

import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    Integer updateView(Long postno);

    List<Post> findAllByUser(User user);

    @Modifying
    @Query("update Post post set post.recommend = post.recommend + 1 where post.postno = :postno")
    Integer updateRecommend(Long postno);


}
