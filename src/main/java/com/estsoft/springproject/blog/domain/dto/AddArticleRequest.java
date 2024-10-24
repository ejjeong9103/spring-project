package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest { // 객체전달을 위한 DTO 클래스
    private String title;
    private String content;


    public Article toEntity() { // Article 형태로 만들수?있는
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
