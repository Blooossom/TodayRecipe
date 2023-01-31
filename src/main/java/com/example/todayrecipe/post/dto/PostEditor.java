package com.example.todayrecipe.post.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class PostEditor {

    private String title;
    private String content;

}
