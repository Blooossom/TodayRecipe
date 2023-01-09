package com.example.todayrecipe.post.service;


import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;

import java.util.List;

public interface PostService {

    List<PostResponse> selectPostList();

    String addPost(PostRequest request);
}
