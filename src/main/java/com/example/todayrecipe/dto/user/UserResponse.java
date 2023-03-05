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
public class UserResponse {

    private Long id;
    private String userId;
    private String nickname;
    private String email;
    private String indate;
    private Post post;
    private Comment comment;

    public UserResponse (User user){
        this.id = user.getId();
        this.userId = user.getUserid();
        this.email = user.getEmail();
        this.indate = user.getIndate();
    }
}
