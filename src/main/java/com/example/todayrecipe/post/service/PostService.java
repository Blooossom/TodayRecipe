package com.example.todayrecipe.post.service;


import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostResponse> selectPostList();

    String addPost(PostRequest request);

    PostResponse viewPost(String postId);
}
