package com.example.todayrecipe.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {

    private Long id;
    private String userId;
    private String nickname;
    private String indate;


}
