package com.example.todayrecipe.admin.service;


import com.example.todayrecipe.admin.dto.AdminReq;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.dto.UserResponse;

import java.util.List;

public interface AdminService {

    String adminLogin(AdminReq adminReq);

    List<UserResponse> viewUserList();

    List<PostResponse> viewPostList();

    List<Post> viewPostFindByUserId(String userId);

    String banUser(String userId);

    String deletePost(String title);

    UserResponse findUserByUserid(String userid);

}
