package com.example.todayrecipe.user.service.impl;

import com.example.todayrecipe.user.dto.UserRequest;
import com.example.todayrecipe.user.entity.User;
import com.example.todayrecipe.user.repository.UserRepository;
import com.example.todayrecipe.user.service.UserService;
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
    public String signUp(UserRequest req) {
        try {
            repo.save(req.toEntity());
        } catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Override
    public String login(UserRequest req) {
        User user = repo.findByUseridAndPassword(req.getUserid(), req.getPassword())
                .orElse(null);
        if (user != null) {
            return "success";
        }
        else {
            return "failed";
        }
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
    public void checkUserIdDuplication(UserRequest req) {
        boolean userIdDuplication = repo.existsByUserid(req.toEntity().getUserid());
        if (userIdDuplication) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkNickNameDuplication(UserRequest req) {
        boolean nicknameDuplication = repo.existsByNickname(req.toEntity().getNickname());
        if (nicknameDuplication) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkEmailDuplication(UserRequest req) {
        boolean emailDuplication = repo.existsByEmail(req.toEntity().getEmail());
        if (emailDuplication) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
}
