package com.example.todayrecipe.dto.post;


import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostResDTO {

    private Long postNo;
    private String title;
    private String content;
    private String writer;
    private int view;
    private int recommend;
    private String created_date;
    private String modified_date;
    private String email;

    public PostResDTO(Post post) {
        this.email = post.getUser().getEmail();
        this.postNo = post.getPostno();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.view = post.getView();
        this.recommend = post.getRecommend();
        this.created_date = post.getCreated_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.modified_date = post.getModified_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


}
