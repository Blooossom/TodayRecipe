package com.example.todayrecipe.user.entity;


import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userid;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String indate;


    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("created_date DESC")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("created_date DESC")
    private List<Comment> comments;

}
