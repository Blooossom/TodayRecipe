package com.example.todayrecipe.dto.comment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCommentReqDTO {

    private Long commentNo;

    private String content;

}
