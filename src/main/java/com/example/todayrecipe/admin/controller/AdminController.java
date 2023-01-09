package com.example.todayrecipe.admin.controller;


import com.example.todayrecipe.admin.dto.AdminReq;
import com.example.todayrecipe.admin.service.AdminService;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.user.dto.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"관리자 컨트롤러"}, description = "관리자 사용 기능을 관리하는 컨트롤러")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService service;

    @ApiOperation(value = "관리자 로그인 기능", notes = "관리자 로그인을 담당하는 API")
    @PostMapping("/adminlogin")
    public String adminLogin(AdminReq adminReq){
        return service.adminLogin(adminReq);
    }

    @ApiOperation(value = "게시글 조회", notes = "현재 작성되어 있는 게시글들을 가져옴")
    @GetMapping("/selectpostlist")
    public List<PostResponse> viewPostList(){
        return service.viewPostList();
    }

    @ApiOperation(value = "유저 조회", notes = "현재 가입되어 있는 유저 목록을 가져오는 API")
    @GetMapping("/selectuserlist")
    public List<UserResponse> viewUserList(){
        return service.viewUserList();
    }

    @ApiOperation(value = "유저 밴", notes = "문제를 일으키는 유저를 추방하는 API")
    @DeleteMapping("/banuser")
    public String banUser(String userid){
        return service.banUser(userid);
    }

    @ApiOperation(value = "게시글 삭제", notes = "이용 기준에 맞지 않는 게시글을 삭제하는 API")
    @DeleteMapping("/deletePost")
    public String deletePost(String title){
        return service.deletePost(title);
    }

    @ApiOperation(value = "유저 검색", notes = "입력된 값으로 유저를 검색해서 반환하는 API")
    @GetMapping("selectUser")
    public UserResponse findUserByUserId(@PathVariable String userid){
        return null;
    }



}
