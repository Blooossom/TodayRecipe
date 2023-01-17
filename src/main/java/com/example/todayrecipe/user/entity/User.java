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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private String userid;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "indate")
    private String indate;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("created_date DESC")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("created_date DESC")
    private List<Comment> comments;

}
