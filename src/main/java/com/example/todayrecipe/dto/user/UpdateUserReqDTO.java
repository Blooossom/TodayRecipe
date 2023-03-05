package com.example.todayrecipe.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserReqDTO {


    private String password;

    private String phone;

    private String nickname;

    private String address;



}
