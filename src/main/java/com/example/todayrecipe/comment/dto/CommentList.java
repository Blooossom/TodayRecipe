package com.example.todayrecipe.comment.dto;
import java.util.List;

import com.example.todayrecipe.util.Pagination;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentList {

    private List<CommentResponse> list;
    private Pagination pagination;

}
