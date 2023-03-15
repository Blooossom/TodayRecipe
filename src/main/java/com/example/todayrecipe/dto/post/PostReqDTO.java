package com.example.todayrecipe.dto.post;


import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
public class PostReqDTO {
    @Getter
    public class WritePost {
        private String title;

        private String content;

        private String tag;
    }
    @Getter
    public class UpdatePost {

        private Long postNo;

        private String title;

        private String content;
    }
}
