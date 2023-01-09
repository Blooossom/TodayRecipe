package com.example.todayrecipe.user.controller;


import com.example.todayrecipe.user.dto.UserRequest;
import com.example.todayrecipe.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Api(tags = {"회원 기본 기능 컨트롤러"}, description = "로그인, 로그아웃, 회원가입, 회원탈퇴 기능 구현")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @ApiOperation(value = "회원 로그인", notes = "회원 로그인 후 세션에 아이디 등록")
    @PostMapping("/login")
    public String login(UserRequest req, HttpSession session){
        String result = service.login(req);
        if (result.equals("success")) {
            session.setAttribute("userid", req.getUserid());
        }
        return result;
    }

    @ApiOperation(value = "회원가입", notes = "유효성, 중복검사 후 문제 없을 경우 회원가입 시키는 API")
    @PostMapping("/signup")
    public String signUp(@Valid UserRequest req, Errors errors, Model model){
        try {
            service.checkUserIdDuplication(req);
            service.checkNickNameDuplication(req);
            service.checkEmailDuplication(req);
        } catch (Exception err) {
            err.printStackTrace();
            return "signup";
        }
        if (errors.hasErrors()) {

            model.addAttribute("req", req);

            Map<String, String> validatorResult = service.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "signup";
        }
        service.signUp(req);
        return "login";
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃 후 세션 삭제 하는 API")
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("userid") != null) {
            session.setAttribute("userid", null);
            return "login";
        }
        return "index";
    }





}
