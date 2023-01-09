package com.example.todayrecipe.comment.dto;

import com.example.climbingassemble.comment.entity.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponse {

    private String nickname;
    private String created_date;
    private String modified_date;
    private String text;
    private String post_id;
    private CommentResponse (Comment comment) {
        this.post_id = String.valueOf(comment.getPost().getPost_id());
        this.nickname = comment.getUser().getNickname();
        this.text = comment.getText();
        this.created_date = comment.getCreated_date();
        this.modified_date = comment.getModified_date();
    }
}
