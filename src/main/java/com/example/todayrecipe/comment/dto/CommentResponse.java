package com.example.todayrecipe.comment.dto;

import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponse {

    private Long id;
    private String writer;
    private String created_date;
    private String modified_date;
    private String content;
    private Post post;
    private User user;
    public CommentResponse (Comment comment) {
        this.id = comment.getId();
        this.writer = comment.getWriter();
        this.content = comment.getContent();
        this.created_date = comment.getCreated_date();
        this.modified_date = comment.getModified_date();
    }
}
