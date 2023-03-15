package com.example.todayrecipe.controller;

import com.example.todayrecipe.dto.Response;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.dto.user.UserReqDTO;
import com.example.todayrecipe.lib.Helper;
import com.example.todayrecipe.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final Response response;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated UserReqDTO.SignUp signUp, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }

        return authService.signUp(signUp);
    }
    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated UserReqDTO.Login login, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }

        return authService.login(login);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        return authService.logout(accessToken);
    }

    /**
     * 토큰 갱신
     */
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated UserReqDTO.Reissue reissue, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return authService.reissue(reissue);
    }

    /**
     * 비밀번호 확인
     */
    @PostMapping("/mypage/check")
    public Map<String, String> checkPassword(@AuthenticationPrincipal LoginReqDTO user, @RequestBody HashMap<String, Objects> password) {
        return authService.checkPassword(user.getEmail(), password.get("password").toString());
    }


}
