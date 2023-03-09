package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.post.PostReqDTO;
import com.example.todayrecipe.dto.post.PostResDTO;
import com.example.todayrecipe.dto.post.UpdatePostReqDTO;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface PostService {

    //레시피 게시판 이동 시 게시글 출력
    List<PostResDTO> selectPostList();

    List<PostResDTO> selectRecommendList();

    List<PostResDTO> selectPostListByEmail(LoginReqDTO user);

    //레시피 작성할 때
    ResponseEntity<String> addPost(PostReqDTO request, LoginReqDTO userRequest);

    //게시글을 눌렀을 때, 게시글을 출력해 줌
    ResponseEntity<PostResDTO> viewPost(HashMap<String, Object> map);

    ResponseEntity<String> deletePost(HashMap<String, Object> map, LoginReqDTO user);

    ResponseEntity<String> updatePost(UpdatePostReqDTO reqDTO, LoginReqDTO user);

    int updateView(Long postNo);

    ResponseEntity<String> updateRecommend(HashMap<String, Object> map);
    PostResDTO getPost(HashMap<String, Object> map);

}
