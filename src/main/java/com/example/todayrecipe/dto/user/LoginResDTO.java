package com.example.todayrecipe.dto.user;

import com.example.todayrecipe.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginResDTO {

    private String email;

    private String name;

    private String token;

    private String message;

    public LoginResDTO(User user, String token) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.token = token;
    }
    public LoginResDTO(String message) {
        this.message = message;
    }


}
