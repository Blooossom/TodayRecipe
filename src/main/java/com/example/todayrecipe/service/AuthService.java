package com.example.todayrecipe.service;

import com.example.todayrecipe.dto.user.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {

    ResponseEntity signUp(UserReqDTO signUpReq);

    ResponseEntity<?> signUp(UserReqDTO.SignUp signUp);

    ResponseEntity<?> login(UserReqDTO.Login login);

    ResponseEntity<?> logout(String accessToken);

    Map<String, String> checkPassword(String email, String password);

    ResponseEntity<?> reissue(UserReqDTO.Reissue reissue);

    ResponseEntity<?> updateUserInfo(UpdateUserReqDTO updateUserReqDTO, String accessToken);

    ResponseEntity<String> signOut(LoginReqDTO loginReqDTO);

    ResponseEntity<?> viewMyInfo(String accessToken);
}
