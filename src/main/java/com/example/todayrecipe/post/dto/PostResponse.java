package com.example.todayrecipe.post.dto;


import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostResponse {

    private String id;
    private String title;
    private String content;
    private String writer;
    private int view;
    private String created_date;
    private String modified_date;
    private User user;

    public PostResponse (Post post) {
        this.id = String.valueOf(post.getId());
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.view = post.getView();
        this.created_date = post.getCreated_date();
        this.modified_date = post.getModified_date();
    }


}
