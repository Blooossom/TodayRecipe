package com.example.todayrecipe.service;

import com.example.todayrecipe.dto.user.UserRequest;
import org.springframework.validation.Errors;

import java.util.Map;

public interface UserService {


    Map<String, String> validateHandling(Errors errors);
    void checkUserIdDuplication(UserRequest req);
    void checkNickNameDuplication(UserRequest req);
    void checkEmailDuplication(UserRequest req);

    String signUp(UserRequest req);

    String checkUser(String userId, String password);

    String signOut(String userId);

    String login(UserRequest req);
}
