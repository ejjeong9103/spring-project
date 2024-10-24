package com.estsoft.springproject.blog.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    public void test() {
        Article article = new Article("제목","내용");

        // builder 어노테이션 사용햇을시 생성하는법
        Article articleBuilder = Article.builder()
                .title("제목")
                .content("내용")
                .build();
    }

}