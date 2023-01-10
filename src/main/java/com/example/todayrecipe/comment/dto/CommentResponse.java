package com.example.todayrecipe.comment.dto;

import com.example.todayrecipe.comment.entity.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponse {

    private String writer;
    private String created_date;
    private String modified_date;
    private String content;
    private String post_id;
    public CommentResponse (Comment comment) {
        this.post_id = String.valueOf(comment.getPost().getId());
        this.writer = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.created_date = comment.getCreated_date();
        this.modified_date = comment.getModified_date();
    }
}
