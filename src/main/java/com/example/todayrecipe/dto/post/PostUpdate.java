package com.example.todayrecipe.dto.post;

import com.example.todayrecipe.util.Pagination;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class PostUpdate {

    private String title;
    private String content;

    @Builder
    public PostUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
