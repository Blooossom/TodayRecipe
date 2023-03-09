package com.example.todayrecipe.controller;

import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.dto.user.UpdateUserReqDTO;
import com.example.todayrecipe.dto.user.UserResDTO;
import com.example.todayrecipe.service.AuthService;
import com.example.todayrecipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final AuthService authService;

    private final UserService userService;

    /**
     * 회원 정보 수정
     */
    @PutMapping("/mypage/updateInfo")
    public ResponseEntity<String> updateUserInfo(@AuthenticationPrincipal LoginReqDTO user, @RequestBody UpdateUserReqDTO updateUserReqDTO) {
        return authService.updateUserInfo(updateUserReqDTO, user);
    }
    @GetMapping("/mypage/myinfo")
    public ResponseEntity<UserResDTO> viewMyInfo(@AuthenticationPrincipal LoginReqDTO reqDTO) {
        return authService.viewMyInfo(reqDTO);
    }








}
