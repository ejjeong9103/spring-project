package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.ArticleCommentsDTO;
import com.estsoft.springproject.blog.domain.dto.CommentRequestDTO;
import com.estsoft.springproject.blog.domain.dto.CommentResponseDTO;
import com.estsoft.springproject.blog.service.BlogService;
import com.estsoft.springproject.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentArticleController {
    CommentService commentService;
    BlogService blogService;


    public CommentArticleController(CommentService commentService, BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }

    // POST /api/articles/{{articleId}}/comments
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentResponseDTO> saveCommentByArticleId(@PathVariable Long articleId,
                                                                     @RequestBody CommentRequestDTO request) {
        Comment comment = commentService.saveComment(articleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponseDTO(comment));
    }

    // GET /api/comments/{commentId}
    @GetMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> selectCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.findComment(commentId);
        return ResponseEntity.ok(new CommentResponseDTO(comment));
    }

    // PUT /api/comments/{commentId}
    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateCommentById(@PathVariable Long commentId,
                                                                @RequestBody CommentRequestDTO request) {
        Comment updated = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(new CommentResponseDTO(updated));
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/api/articles/{articleId}/comments")
//    public ResponseEntity<List<ArticleCommentsDTO>> getAllArticlesWithComments(@PathVariable Long articleId) {
//        List<ArticleCommentsDTO> articlesWithComments = blogService.findAllComments();
//        return ResponseEntity.ok(articlesWithComments);
//    }
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentResponseDTO> comments = commentService.findAllCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }


}