package com.example.todayrecipe.dto.comment;


import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentReqDTO {
    private Long commentNo;
    private String writer;

    private String created_date;

    private String modified_date;
    private String content;
    private Long postNo;
    private String email;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .writer(user.getNickname())
                .content(content)
                .build();
    }





}
