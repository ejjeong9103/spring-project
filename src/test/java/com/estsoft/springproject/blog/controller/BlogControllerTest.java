package com.estsoft.springproject.blog.controller;

import com.estsoft.springproject.blog.domain.dto.AddArticleRequest;
import com.estsoft.springproject.blog.domain.Article;
import com.estsoft.springproject.blog.domain.dto.UpdateArticleRequest;
import com.estsoft.springproject.blog.repository.BlogRepository;
import com.estsoft.springproject.blog.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; // jackson을 사용하기 위한 의존성 주입

    @Autowired
    private BlogRepository repository;

    @Autowired
    private BlogService blogService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        repository.deleteAll(); // 조회할때 기존 데이터가 있을수 있으니 삭제

    }

    // 블로그 게시글 만들기 API(POST)
    @Test
    public void addArticle() throws Exception {
        // given: article 저장
//        Article article = new Article("제목","내용");
        AddArticleRequest request = new AddArticleRequest("제목", "내용");
        // 직렬화 (object -> json)
        String json = objectMapper.writeValueAsString(request);

        // when: POST /articles API 호출
        ResultActions resultActions = mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        // then: 호출 결과 검증
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()));

        List<Article> articleList = repository.findAll(); // repository의 Article 객체 갯수를 리스트로
        Assertions.assertThat(articleList.size()).isEqualTo(1); // 1개 맞는지

    }

    // 블로그 게시글 조회 API(GET)

    @Test
    public void findAll() throws Exception {
        // given: 조회 API에 필요한 값 세팅
        Article article = repository.save(new Article("title", "content"));

        // when: 조회 API
        ResultActions resultActions = mockMvc.perform(get("/articles")
                .accept(MediaType.APPLICATION_JSON));

        // then: API 호출 결과 검증 json을 Map으로
        resultActions
                .andExpect(jsonPath("$[0].title").value(article.getTitle()))
                .andExpect(jsonPath("$[0].content").value(article.getContent()))
                .andExpect(status().isOk());
    }

    // 블로그 게시물 단건 조회 : data insert(id=1) , GET /articles/1
    @Test
    public void findOne() throws Exception {
        // given: data insert
        Article article = repository.save(new Article("title", "content"));
        Long id = article.getId(); // id 가져오기

        // when: API 호출
        ResultActions resultActions = mockMvc.perform(get("/articles/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        // then: API 호출 결과 검증 (given절에서 추가한 데이터가 그대로 json 형태로 넘어오는지)
        resultActions
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()))
                .andExpect(status().isOk());
    }

    // 단건 조회 API - id에 해당하는 자원이 없을경우 4xx 예외처리 검증
    @Test
    public void findOneException() throws Exception {

        // when: API 호출
        ResultActions resultActions = mockMvc.perform(get("/articles/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON));
        // then: Exception에 대한 검증, resultActions Status Code 검증
        resultActions.andExpect(status().isBadRequest());

        assertThrows(IllegalArgumentException.class,
                () -> blogService.findBy(1L));

    }

    @Test
    public void deleteTest() throws Exception {
        // given: data insert
        Article article = repository.save(new Article("title", "content"));
        Long id = article.getId();

        // when: DELETE /articles/{id} API 호출
        ResultActions resultActions = mockMvc.perform(delete("/articles/{id}", id));

        // then: API 호출 결과 검사
        resultActions.andExpect(status().isOk()); // 여기서 검증 종료
        List<Article> articleList = repository.findAll(); // 확인을 위한 리스트
        Assertions.assertThat(articleList).isEmpty(); // 비었는지?
    }

    // PUT /articles/{id}     body(json content) 요청
    @Test
    public void updateArticle() throws Exception {
        // given:
        Article article = repository.save(new Article("blog title", "blog content"));
        Long id = article.getId();

        // 수정 데이터 (object -> json)
        UpdateArticleRequest request = new UpdateArticleRequest("변경제목", "변경내용");
        String updateJsonContent = objectMapper.writeValueAsString(request);
        // when:
        ResultActions resultActions = mockMvc.perform(put("/articles/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJsonContent)
        );

        // then:
        resultActions
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(status().isOk());
    }

    @Test
    public void updateArticleException() throws Exception {
        // given: id, requestBody 정의
        Long notExistId = 33L;
        UpdateArticleRequest request = new UpdateArticleRequest("title","content");
        String requestBody = objectMapper.writeValueAsString(request); // json 형태의 string 타입



        // when: 수정 API 호출     (/articles/{id}, requestBody)
        ResultActions resultActions = mockMvc.perform(put("/articles/{id}", notExistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        // then 400 Bad Request
        resultActions.andExpect(status().isBadRequest());
        assertThrows(IllegalArgumentException.class, // 첫번째 인자: 발생하고있는 exception
                ()-> blogService.update(notExistId,request));
    }



//    @Test
//    public void updateArticleException() throws Exception {
//        UpdateArticleRequest request = new UpdateArticleRequest("변경제목", "변경내용");
//        Long id = 1L;
//        String updateJsonContent = objectMapper.writeValueAsString(request);
//
//
//        ResultActions resultActions = mockMvc.perform(put("/articles/{id}", id)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(updateJsonContent));
//
//        resultActions.andExpect(status().isBadRequest());
//        assertThrows(IllegalArgumentException.class,
//                () -> blogService.update(id, request));
//    }
}
