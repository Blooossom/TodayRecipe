package com.example.todayrecipe.dto.user;

import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserRequest {

    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    private String userid;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8 ~ 16글자의 영문 대소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    //@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$}", message = "닉네임은 특수문자를 제외한 2 ~ 10자리로 이루어져야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    //@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "주소는 필수 입력 사항입니다.")
    private String address;
    private Post post;
    private Comment comment;

    public User toEntity() {
        return User.builder()
                .userid(userid)
                .password(password)
                .nickname(nickname)
                .email(email)
                .address(address)
                .build();
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
    public UserRequest(Claims claims) {
        this.email = claims.get("email", String.class);
    }

}
