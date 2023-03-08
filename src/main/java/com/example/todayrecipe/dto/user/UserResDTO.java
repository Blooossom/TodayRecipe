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

    private Long id;

    private String email;

    private String nickname;

    private String name;

    private String phone;
    private String indate;

    private Post post;

    private Comment comment;

    public UserResDTO(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.phone = user.getPhone();
        this.indate = user.getIndate();
    }
}
