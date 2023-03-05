package com.example.todayrecipe.dto.post;

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
