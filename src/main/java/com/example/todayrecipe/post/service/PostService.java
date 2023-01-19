package com.example.todayrecipe.post.service;


import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    //레시피 게시판 이동 시 게시글 출력
    List<PostResponse> selectPostList();

    //레시피 작성할 때
    String addPost(PostRequest request, String user_id);

    //게시글을 눌렀을 때, 게시글을 출력해 줌
    PostResponse viewPost(Long postId);

    String deletePost(Long postId);

    String updatePost(Long postId);

    int updateView(Long id);

    PostResponse getPost(Long postId);

}
