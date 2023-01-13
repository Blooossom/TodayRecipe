package com.example.todayrecipe.user.dto;

import com.example.todayrecipe.user.entity.Role;
import com.example.todayrecipe.user.entity.User;
import lombok.Getter;

@Getter
public class UserSessionDTO {
    private String userId;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    public UserSessionDTO(User user){
        this.userId = user.getUserid();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }






}
