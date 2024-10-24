package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleResponse;
import com.estsoft.springproject.blog.domain.dto.UpdateArticleRequest;
import com.estsoft.springproject.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// @Slf4j - 로깅을 위한 어노테이션
@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "블로그 저장/수정/삭제/조회 API", description = "API 설명을 여기에 작성")
public class BlogController {
    BlogService service;

    public BlogController(BlogService service) {
        this.service = service;
    }

    // Request Mapping(생성 -> POST) / (특정 url      POST/articles)
//    @RequestMapping(method = RequestMethod.POST) = 아래 어노테이션과 같음
    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> writeArticle(@RequestBody AddArticleRequest request) {
        // ResponseEntity<Article>는 statusCode를 넣어주기 위해 사용

        // logging level
        // trace, debug, info, warn, error
        log.debug("{} , {}", request.getTitle(), request.getContent());
        // 중괄호 순서대로 뒤의 값을 받아서 출력

        Article article = service.saveArticle(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(article.convert()); // .body에 build를 가지고 있으므로 생략
//                .build(); // 성공 201
    }

    // Request Mapping(조회 -> GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "100", description = "요청에 성공했습니다." , content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "블로그 전체 목록 보기", description = "블로그 메인 화면에서 보여주는 전체 목록")
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> findAll() {
        List<Article> articleList = service.findAll();
        // List<ArticleResponse> 형태로 변환
        // stream 사용
        List<ArticleResponse> responseList = articleList.stream()
                .map(Article::convert)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    // 단건 조회 API (Request mapping) 만들기  GET /articles/1 , /articles/{}
    @Parameter(name = "id", description = "블로그 글 ID", example = "45")
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findBy(@PathVariable Long id) {

        Article article = service.findBy(id); // Exception (5xx) -> 4xx Status code
        // Article -> ArticleResponse 형태로 변환
        return ResponseEntity.ok(article.convert());
    }

    // DELETE /articles / {id}
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteBy(id);
        return ResponseEntity.ok().build();
    }

    // PUT / articles/{id} 수정 API request body
    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateById(@PathVariable Long id,
                                                      @RequestBody UpdateArticleRequest request) {
                                                      // DTO형태로 받음(title,content)
        Article updateArticle = service.update(id, request);
        return ResponseEntity.ok(updateArticle.convert());
    }



    // IlligalArgumentException 발생시 여기로 이동 -> aop에 global로 옮겨짐
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // reason: "" 형식
//    }
}
