package com.example.todayrecipe.post.dto;


import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int view;
    private int recommend;
    private String created_date;
    private String modified_date;
    private User user;

    public PostResponse (Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.view = post.getView();
        this.recommend = post.getRecommend();
        this.created_date = post.getCreated_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.modified_date = post.getModified_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


}
