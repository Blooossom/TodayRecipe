package com.example.todayrecipe.entity;

import com.example.todayrecipe.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseTimeEntity {


    @Id
    @Column(name = "postno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(name = "tag")
    private String tag;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String writer;

    @Column(columnDefinition = "integer default 0")
    private int view;

    @Column(columnDefinition = "integer default 0")
    private int recommend;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
