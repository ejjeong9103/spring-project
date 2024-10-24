package com.estsoft.springproject.blog.controller;


import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.ArticleViewResponse;
import com.estsoft.springproject.blog.service.BlogService;
import com.estsoft.springproject.user.domain.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogPageController {

    private final BlogService blogService; // 의존성 주입

    public BlogPageController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/articles")
    public String showArticle(Model model) {
        List<Article> articleList = blogService.findAll();

        List<ArticleViewResponse> list = articleList.stream()
                .map(ArticleViewResponse::new)
                .toList();

        model.addAttribute("articles", list);
        return "articleList"; // articleList.html
    }

    // GET /articles/{id} 상세 페이지 리턴
    @GetMapping("/articles/{id}")
    public String showDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal Users users) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Users principal = (Users) authentication.getPrincipal();
        // article -> articleViewResponse
        Article article = blogService.findBy(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    // GET  /new-articles?id=1
    @GetMapping("/new-article")       // 생성은 뒤에 id가 없으므로 (required =false) -> 항상 필요한건 아님
    public String addArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { // 새로운 게시글 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else { // 게시글 수정 -> 기존 게시글 불러오기
            Article article = blogService.findBy(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}

