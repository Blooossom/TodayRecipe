package com.example.todayrecipe.comment.entity;


import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String writer;

    @Column
    private String created_date;

    @Column
    private String modified_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    public void update(String content){
        this.content = content;
        this.modified_date = modified_date;
    }

}
