package com.estsoft.springproject.blog.domain.dto;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.util.DateFormatUtil;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private Long articleId;
    private String body;
    private String createdAt;  // yyyy-MM-dd HH:mm
    private ArticleResponse article;


    public CommentResponseDTO(Comment comment) {
        Article articleFromComment = comment.getArticle();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        commentId = comment.getCommentId();
        articleId = articleFromComment.getId();
        body = comment.getBody();
        createdAt = comment.getCreatedAt().format(DateFormatUtil.formatter);

        article = new ArticleResponse(comment.getArticle());
    }

}
