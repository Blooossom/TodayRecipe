package com.example.todayrecipe.admin.service;


import com.example.todayrecipe.admin.dto.AdminReq;
import com.example.todayrecipe.dto.post.PostResponse;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.dto.user.UserResponse;

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
