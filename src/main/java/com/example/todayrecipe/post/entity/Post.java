package com.example.todayrecipe.post.entity;

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
public class Post {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String writer;

    @Column(columnDefinition = "integer default 0")
    private int view;

    @Column
    private String created_date;

    @Column
    private String modified_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
        this.modified_date = modified_date;
    }

}
