package com.example.todayrecipe.admin.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminReq {

    private String adminId;

    private String adminPassword;


}
