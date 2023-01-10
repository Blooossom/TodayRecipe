package com.example.todayrecipe.post.dto;


import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostRequest {


    private Long id;
    private String title;
    private String content;
    private String writer;
    private int view;
    private String created_date;
    private String modified_date;
    private User user;

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .view(0)
                .user(user)
                .build();
    }
}
