package com.example.todayrecipe.post.entity;

import com.example.todayrecipe.post.dto.PostEditor;
import com.example.todayrecipe.user.entity.User;
import com.example.todayrecipe.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseTimeEntity {


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

    @Column(columnDefinition = "integer default 0")
    private int recommend;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor(){
        return PostEditor.builder()
                .title(title)
                .content(content);
    }
    public void edit(PostEditor postEditor) {
        title =  postEditor.getTitle();
        content = postEditor.getContent();
    }

}
