package com.memil.memilog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.memil.memilog.domain.Post;
import com.memil.memilog.repository.PostRepository;
import com.memil.memilog.request.PostCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "memilog.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("글 단건 조회 테스트")
    void test1() throws Exception {
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        mockMvc.perform(get("/posts/{postId}", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-inquiry", pathParameters(
                                parameterWithName("postId").description("게시글 ID입니다")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 ID입니다"),
                                fieldWithPath("title").description("제목입니다"),
                                fieldWithPath("content").description("내용입니다")
                        )
                ));
    }
    @Test
    @DisplayName("글 등록 테스트")
    void test2() throws Exception {
        PostCreate request = PostCreate.builder()
                        .title("나는 바1보")
                        .content("너도 바보")
                        .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").description("제목")
                                        .attributes(Attributes.key("constraint").value("좋은 제목 입력해주세요")),
                                fieldWithPath("content").description("내용")
                        )));
    }
}

