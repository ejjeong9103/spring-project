package com.estsoft.springproject.blog.service;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleCommentsDTO;
import com.estsoft.springproject.blog.domain.dto.CommentResponseDTO;
import com.estsoft.springproject.blog.domain.dto.UpdateArticleRequest;
import com.estsoft.springproject.blog.repository.BlogRepository;
import com.estsoft.springproject.blog.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    private final BlogRepository repository;
    private final CommentRepository commentRepository;


    // repository.save(Article)을 쓰기위한 의존성 주입


    public BlogService(BlogRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }
    // blog 게시글을 저장해주는 역할

    public Article saveArticle(AddArticleRequest request) {

        return repository.save(request.toEntity()); // Article 타입으로 컨버팅 한후 save 메소드로 넘김
    }


    // blog 게시글 전체 조회

    public List<Article> findAll() {
        return repository.findAll();
    }

    // blog 게시글 단건 id(PK) 조회
    public Article findBy(Long id) {
        // Optional 객체 꺼내오기
//       return repository.findById(id).orElse(new Article());
//        return repository.findById(id)
//                .orElseGet(Article::new);
        return repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found id: " + id));
    }

    // blog 게시글 DB 삭제 (id)
    // 삭제 후에는 return 값이 필요없으니 void 타입
    public void deleteBy(Long id) {
        repository.deleteById(id);
    }

    // blog 게시글 수정

    // Transactional 어노테이션
    // -성공 -> commit;
    // -실패(RuntimeException) -> rollback;
    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = findBy(id); // 수정하고자 하는 row(article 객체)
        article.update(request.getTitle(), request.getContent());
        return article;
    }

}
