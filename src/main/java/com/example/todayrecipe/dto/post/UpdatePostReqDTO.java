package com.example.todayrecipe.dto.post;


import com.example.todayrecipe.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostReqDTO {

    private Long postNo;

    private String title;

    private String content;


}
