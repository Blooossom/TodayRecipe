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
    private Long user_id;

    public Post toEntity(){
        return Post.builder()
                .user(User.builder().id(user_id).build())
                .title(title)
                .writer(writer)
                .content(content)
                .view(0)
                .recommend(0)
                .build();
    }
}
