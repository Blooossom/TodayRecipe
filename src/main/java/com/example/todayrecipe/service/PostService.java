package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.post.PostRequest;
import com.example.todayrecipe.dto.post.PostResponse;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public interface PostService {

    //레시피 게시판 이동 시 게시글 출력
    List<PostResponse> selectPostList();

    List<PostResponse> selectRecommendList();

    List<PostResponse> selectPostListByEmail(LoginReqDTO user);

    //레시피 작성할 때
    ResponseEntity<String> addPost(PostRequest request, LoginReqDTO userRequest);

    //게시글을 눌렀을 때, 게시글을 출력해 줌
    ResponseEntity<PostResponse> viewPost(HashMap<String, Object> map);

    ResponseEntity<String> deletePost(HashMap<String, Object> map, LoginReqDTO user);

    ResponseEntity<String> updatePost(PostRequest request, LoginReqDTO user);

    int updateView(HashMap<String, Object> map);

    int updateRecommend(HashMap<String, Object> map);
    PostResponse getPost(HashMap<String, Object> map);

}
