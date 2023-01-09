package com.example.todayrecipe.comment.dto;


import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentRequest {
    private String id;
    private String writer;
    private String created_date;
    private String modified_date;
    private String text;
    private Post post;
    private User user;

    public Comment toEntity(CommentRequest request){
        return Comment.builder()
                .post(post)
                .user(user)
                .writer(writer)
                .text(text)
                .build();
    }


}
