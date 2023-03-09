package com.example.todayrecipe.service;

import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.dto.user.LoginResDTO;
import com.example.todayrecipe.dto.user.UpdateUserReqDTO;
import com.example.todayrecipe.dto.user.UserReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    ResponseEntity signUp(UserReqDTO signUpReq);

    ResponseEntity<LoginResDTO> login(LoginReqDTO loginReqDTO);

    Map<String, String> checkPassword(String email, String password);

    ResponseEntity<String> updateUserInfo(UpdateUserReqDTO updateUserReqDTO, LoginReqDTO user);

    ResponseEntity<String> signOut(LoginReqDTO loginReqDTO);
}
