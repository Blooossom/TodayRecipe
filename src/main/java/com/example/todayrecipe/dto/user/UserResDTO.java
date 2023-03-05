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
    private String nickname;
    private String email;
    private String indate;
    private Post post;
    private Comment comment;

    public UserResDTO(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.indate = user.getIndate();
    }
}
