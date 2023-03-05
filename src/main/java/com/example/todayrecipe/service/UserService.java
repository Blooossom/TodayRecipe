package com.example.todayrecipe.service;

import com.example.todayrecipe.dto.user.UserReqDTO;
import org.springframework.validation.Errors;

import java.util.Map;

public interface UserService {


    Map<String, String> validateHandling(Errors errors);
    void checkUserIdDuplication(UserReqDTO req);
    void checkNickNameDuplication(UserReqDTO req);
    void checkEmailDuplication(UserReqDTO req);


    String checkUser(String userId, String password);

    String signOut(String userId);

}
