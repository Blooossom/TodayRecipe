package com.example.todayrecipe.dto.user;

import com.example.todayrecipe.entity.User;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LoginReqDTO {

    private String email;

    private String password;

    public LoginReqDTO(Claims claims) {
        this.email = claims.get("email", String.class);
    }

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
