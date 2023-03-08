package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.user.UserReqDTO;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;




    @Override
    public String checkUser(String email, String password) {
            User user = repo.findByEmail(email).orElse(null);
            if (user.getPassword().equals(password)) {
                return "success";
            }
            return "failed";
    }





    @Override
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors){
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Override
    @Transactional(readOnly = true)
    public void checkUserIdDuplication(UserReqDTO req) {
        boolean userIdDuplication = repo.existsByEmail(req.getEmail());
        if (userIdDuplication) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkNickNameDuplication(UserReqDTO req) {
        boolean nicknameDuplication = repo.existsByNickname(req.toEntity().getNickname());
        if (nicknameDuplication) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkEmailDuplication(UserReqDTO req) {
        boolean emailDuplication = repo.existsByEmail(req.toEntity().getEmail());
        if (emailDuplication) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
}
