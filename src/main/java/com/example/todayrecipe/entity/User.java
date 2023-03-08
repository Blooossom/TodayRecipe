package com.example.todayrecipe.entity;


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

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "indate")
    private String indate;

    @Column(name = "role")
    private String role;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("created_date DESC")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("created_date DESC")
    private List<Comment> comments;

}
