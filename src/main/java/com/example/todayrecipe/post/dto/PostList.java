package com.example.todayrecipe.post.dto;

import com.example.todayrecipe.util.Pagination;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostList {

    private List<PostResponse> list;
    private Pagination pagination;

}
