package com.example.todayrecipe.dto.comment;

import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import lombok.*;

import java.time.format.DateTimeFormatter;

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
        this.created_date = comment.getCreated_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.modified_date = comment.getModified_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;
    }
}
