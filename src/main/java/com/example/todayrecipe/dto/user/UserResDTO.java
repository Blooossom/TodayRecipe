package com.example.todayrecipe.dto.user;

import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResDTO {

    private String email;

    private String nickname;

    private String name;

    private String phone;
    private String address;

    private Post post;

    private Comment comment;

    public UserResDTO(User user){
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.address = user.getAddress();
    }
}
