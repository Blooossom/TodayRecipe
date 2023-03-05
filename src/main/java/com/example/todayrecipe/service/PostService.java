package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.post.PostRequest;
import com.example.todayrecipe.dto.post.PostResponse;

import java.util.List;

public interface PostService {

    //레시피 게시판 이동 시 게시글 출력
    List<PostResponse> selectPostList();

    List<PostResponse> selectRecommendList();

    List<PostResponse> selectPostListByUserid(String userId);

    //레시피 작성할 때
    String addPost(PostRequest request, String user_id);

    //게시글을 눌렀을 때, 게시글을 출력해 줌
    PostResponse viewPost(Long postId);

    String deletePost(Long postId, String userId);

    String updatePost(PostRequest request, String userId);

    int updateView(Long id);

    int updateRecommend(Long id);
    PostResponse getPost(Long postId);

}
