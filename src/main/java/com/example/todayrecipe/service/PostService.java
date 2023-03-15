package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.post.PostReqDTO;
import com.example.todayrecipe.dto.post.PostResDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface PostService {

    //레시피 게시판 이동 시 게시글 출력
    ResponseEntity<?> selectPostList();

    ResponseEntity<?> selectRecommendList();

    ResponseEntity<?> selectPostListByEmail(String accessToekn);

    //레시피 작성할 때
    ResponseEntity<?> addPost(PostReqDTO.WritePost writeReq, String accessToken);

    //게시글을 눌렀을 때, 게시글을 출력해 줌
    ResponseEntity<?> viewPost(Long postNo);

    ResponseEntity<?> recommendListRedis();

    ResponseEntity<?> deletePost(Long postNo, String accessToken);

    ResponseEntity<?> updatePost(PostReqDTO.UpdatePost updateReq, String accessToken);

    int updateView(Long postNo);

    ResponseEntity<?> updateRecommend(HashMap<String, Object> map);
    PostResDTO getPost(HashMap<String, Object> map);

}
