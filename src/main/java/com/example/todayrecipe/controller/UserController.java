package com.example.todayrecipe.controller;


import com.example.todayrecipe.dto.Response;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.dto.user.LoginResDTO;
import com.example.todayrecipe.dto.user.UserReqDTO;
import com.example.todayrecipe.lib.Helper;
import com.example.todayrecipe.service.AuthService;
import com.example.todayrecipe.service.TokenService;
import com.example.todayrecipe.service.UserService;
import com.example.todayrecipe.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Api(tags = {"회원 기본 기능 컨트롤러"}, description = "로그인, 로그아웃, 회원가입, 회원탈퇴 기능 구현")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService service;
    private final UsersService usersService;

    private final AuthService authService;
    private final Response response;

    private final TokenService tokenService;

    /**
     * 로그인
     * @param request
     * @return
     */
    @ApiOperation(value = "회원 로그인", notes = "회원 로그인 후 세션에 아이디 등록")
    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@RequestBody LoginReqDTO request){
        return authService.login(request);
    }

    /**
     * 회원가입
     */
    @ApiOperation(value = "회원가입", notes = "유효성, 중복검사 후 문제 없을 경우 회원가입 시키는 API")
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserReqDTO requset){
        return authService.signUp(requset);
    }

    /**
     * 로그아웃
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
     */
    @PostMapping("/mypage/check")
    public Map<String, String> checkPassword(@AuthenticationPrincipal LoginReqDTO user, @RequestBody HashMap<String, Objects> password) {
        return authService.checkPassword(user.getEmail(), password.get("password").toString());
    }
    /**
     * Login
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserReqDTO.SignUp signUp, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.signUp(signUp);
    }

    @PostMapping("/loginTest")
    public ResponseEntity<?> login(@RequestBody UserReqDTO.Login login) {
        // validation check
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
        return usersService.login(login);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated UserReqDTO.Reissue reissue, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.reissue(reissue);
    }

    @PostMapping("/logoutTest")
    public ResponseEntity<?> logoutTest(@RequestHeader(name = "Authorization") String token) {
        System.out.println(token);

        // validation check
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
        return usersService.logout(token);
    }

    @GetMapping("/authority")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_ADMIN");
        return usersService.authority();
    }

    @GetMapping("/userTest")
    public ResponseEntity<?> userTest() {
        log.info("ROLE_USER TEST");
        return response.success();
    }

    @GetMapping("/adminTest")
    public ResponseEntity<?> adminTest() {
        log.info("ROLE_ADMIN TEST");
        return response.success();
    }
}
