package com.example.todayrecipe.dto.user;

import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserReqDTO {
    @Getter
    @Setter
    public static class SignUp {
        @NotBlank(message = "이메일은 필수 입력 사항입니다.")
        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8 ~ 16글자의 영문 대소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$}", message = "닉네임은 특수문자를 제외한 2 ~ 10자리로 이루어져야 합니다.")
        private String nickname;

        private String name;

        private String phone;

        private String role;

        @NotBlank(message = "주소는 필수 입력 사항입니다.")
        private String address;
    }
    @Getter
    @Setter
    public static class Login {
        @NotBlank(message = "이메일은 필수 입력 사항입니다.")
        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8 ~ 16글자의 영문 대소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Getter
    @Setter
    public static class Reissue {
        @NotBlank(message = "accessToken을 입력해주세요")
        private String accessToken;

        @NotBlank(message = "refreshToken을 입력해주세요")
        private String refreshToken;

    }

    @Getter
    @Setter
    public static class Logout {
        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    //@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8 ~ 16글자의 영문 대소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    //@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$}", message = "닉네임은 특수문자를 제외한 2 ~ 10자리로 이루어져야 합니다.")
    private String nickname;

    private String name;

    private String phone;


    @NotBlank(message = "주소는 필수 입력 사항입니다.")
    private String address;

    private Post post;

    private Comment comment;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .phone(phone)
                .address(address)
                .build();
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
    public UserReqDTO(Claims claims) {
        this.email = claims.get("email", String.class);
    }

}
