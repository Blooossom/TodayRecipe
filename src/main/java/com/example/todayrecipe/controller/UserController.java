package com.example.todayrecipe.controller;


import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.dto.user.LoginResDTO;
import com.example.todayrecipe.dto.user.UserReqDTO;
import com.example.todayrecipe.service.AuthService;
import com.example.todayrecipe.service.TokenService;
import com.example.todayrecipe.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Api(tags = {"회원 기본 기능 컨트롤러"}, description = "로그인, 로그아웃, 회원가입, 회원탈퇴 기능 구현")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final AuthService authService;

    private final TokenService tokenService;

    /**
     * 로그인
     * @param request
     * @return
     */
    @ApiOperation(value = "회원 로그인", notes = "회원 로그인 후 세션에 아이디 등록")
    @PostMapping("/login")
    public LoginResDTO login(@RequestBody LoginReqDTO request){
        return authService.login(request);
    }

    /**
     * 회원가입
     * @param requset
     * @return
     */
    @ApiOperation(value = "회원가입", notes = "유효성, 중복검사 후 문제 없을 경우 회원가입 시키는 API")
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserReqDTO requset){
        return authService.signUp(requset);
    }

    /**
     * 로그아웃
     * @param token
     * @return
     */
    @ApiOperation(value = "로그아웃", notes = "로그아웃 후 세션 삭제 하는 API")
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestHeader(name = "Authorization") String token) {
        return tokenService.logout(token);
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/signout")
    public ResponseEntity<String> signout(@AuthenticationPrincipal LoginReqDTO user) {
        return authService.signOut(user);
    }

    /**
     * 비밀번호 확인
     * @param user
     * @param password
     * @return
     */
    @PostMapping("/mypage/check")
    public Map<String, String> checkPassword(@AuthenticationPrincipal LoginReqDTO user, @RequestBody HashMap<String, Objects> password) {
        return authService.checkPassword(user.getEmail(), password.get("password").toString());
    }
}
