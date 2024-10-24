package com.estsoft.springproject.blog.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column
    private String body;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;


    public Comment(String body, Article article) {
        this.body = body;
        this.article = article; // commentID와 createdAt 은 자동 지정
        this.createdAt = LocalDateTime.now(); // createdAt은 현재 시간으로 ���기화
    }
    public void updateCommentBody(String body){
        this.body = body;
    }
}
