package com.example.todayrecipe.admin.service.impl;

import com.example.todayrecipe.admin.dto.AdminReq;
import com.example.todayrecipe.admin.repository.AdminRepository;
import com.example.todayrecipe.admin.service.AdminService;
import com.example.todayrecipe.dto.post.PostResponse;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repo;


    @Override
    public String adminLogin(AdminReq adminReq) {

            return null;

    }

    @Override
    public List<UserResponse> viewUserList() {
        return null;
    }

    @Override
    public List<PostResponse> viewPostList() {
        return null;
    }

    @Override
    public List<Post> viewPostFindByUserId(String userId) {
        return null;
    }

    @Override
    public String banUser(String userId) {
        return null;
    }

    @Override
    public String deletePost(@PathVariable String title) {
        try{
            repo.deleteById(title);
        }catch (Exception err){
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }


    //수정 필요할 듯
    @Override
    public UserResponse findUserByUserid(String userid) {
        return null;
    }
}
