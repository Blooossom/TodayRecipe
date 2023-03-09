package com.example.todayrecipe.dto.post;


import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostReqDTO {

    private Long postNo;

    private String title;

    private String content;

    private String tag;

    private int view;

    private int recommend;

    private String created_date;

    private String modified_date;

    private String email;

    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .writer(user.getNickname())
                .tag(tag)
                .view(view)
                .recommend(recommend)
                .build();
    }


}
