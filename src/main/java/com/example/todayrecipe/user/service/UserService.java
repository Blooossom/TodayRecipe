package com.example.todayrecipe.user.service;

import com.example.todayrecipe.user.dto.UserRequest;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {


    Map<String, String> validateHandling(Errors errors);
    void checkUserIdDuplication(UserRequest req);
    void checkNickNameDuplication(UserRequest req);
    void checkEmailDuplication(UserRequest req);

    String signUp(UserRequest req);

    String checkUser(String userId, String password);

    String login(UserRequest req);
}
