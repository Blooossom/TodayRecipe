package com.example.todayrecipe.comment.dto;


import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentRequest {
    private Long id;
    private String writer;
    private String created_date;
    private String modified_date;
    private String content;
    private Long post_id;
    private Long user_id;

    public Comment toEntity(CommentRequest request){
        return Comment.builder()
                .post(Post.builder().id(post_id).build())
                .user(User.builder().id(user_id).build())
                .writer(writer)
                .content(content)
                .build();
    }


}
