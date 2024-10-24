package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.Comment;
import com.estsoft.springproject.blog.domain.dto.ArticleCommentsDTO;
import com.estsoft.springproject.blog.domain.dto.CommentRequestDTO;
import com.estsoft.springproject.blog.domain.dto.CommentResponseDTO;
import com.estsoft.springproject.blog.repository.BlogRepository;
import com.estsoft.springproject.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private BlogRepository blogRepository;
    private CommentRepository commentRepository; // CommentRepository를 DI 해서 CommentRepository를 ���기화

    public CommentService(BlogRepository blogRepository, CommentRepository commentRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
    }

    public Comment saveComment(Long articleId, CommentRequestDTO requestDTO) {
        // BlogRepository를 DI 해서 Article 객체를 들고옴
        Article article = blogRepository.findById(articleId).orElseThrow(); // NoSuchElementException

        return commentRepository.save(new Comment(requestDTO.getBody(), article));
    }

    public Comment findComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElse(new Comment());
    }

    public Comment updateComment(Long commentId, CommentRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.updateCommentBody(requestDTO.getBody());
        return commentRepository.save(comment);
//
//        Comment comment = findComment(commentId);
//        comment.update(requestDTO.getBody());
//        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId); // delete from comment where id = ${commentId}
    }

    public List<CommentResponseDTO> findAllCommentsByArticleId(Long articleId) {
        Article article = blogRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + articleId));

        return commentRepository.findByArticle(article)
                .stream()
                .map(CommentResponseDTO::new)
                .toList();
    }
}
