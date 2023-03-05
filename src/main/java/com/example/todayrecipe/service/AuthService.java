package com.example.todayrecipe.service;

import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.dto.user.LoginResDTO;
import com.example.todayrecipe.dto.user.UserReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    ResponseEntity signUp(UserReqDTO signUpReq);

    LoginResDTO login(LoginReqDTO loginReqDTO);

    Map<String, String> checkPassword(String email, String password);
}
