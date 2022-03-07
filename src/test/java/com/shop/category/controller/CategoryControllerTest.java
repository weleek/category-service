package com.shop.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.category.dto.CategoryDto;
import com.shop.category.dto.CategoryResponseDto;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.service.CategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryControllerTest {

    private final static String URI = "/category";

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private CategoryDto categoryDto;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        List<CategoryDto> smallDto = Collections.singletonList(CategoryDto.builder()
                .name("소분류")
                .build());

        List<CategoryDto> mediumDto = Collections.singletonList(CategoryDto.builder()
                .name("중분류")
                .children(smallDto)
                .build());

        categoryDto = CategoryDto.builder()
                .name("대분류")
                .children(mediumDto)
                .build();
    }

    @Test
    void 카테고리_생성() throws Exception {
        // given
        when(categoryService.save(any(CategoryDto.class))).thenReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.response.categoryId").value(1));
    }

    @Test
    void 카테고리_수정() throws Exception {
        // given
        CategoryDto updateDto = CategoryDto.builder()
                .id(2L)
                .name("중분류 변경")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(patch(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateDto)))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0000"));

        verify(categoryService).update(ArgumentMatchers.refEq(updateDto));
    }

    @Test
    void 카테고리_삭제() throws Exception {
        // given
        Long categoryId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(delete(URI + "/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0000"));

        verify(categoryService).delete(categoryId);
    }

    @Test
    void 카테고리_조회() throws Exception {
        // given
        CategoryResponseDto dto1 = CategoryResponseDto.builder()
                .categoryId(1L)
                .name("부모")
                .isDelete(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        CategoryResponseDto dto2 = CategoryResponseDto.builder()
                .parentId(1L)
                .parentName("부모")
                .categoryId(2L)
                .name("자식1")
                .isDelete(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        CategoryResponseDto dto3 = CategoryResponseDto.builder()
                .parentId(1L)
                .parentName("부모")
                .categoryId(3L)
                .name("자식2")
                .isDelete(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<CategoryResponseDto> categoryResponses = Arrays.asList(dto1, dto2, dto3);

        when(categoryService.getCategories(any(CategorySearchDto.class)))
                .thenReturn(categoryResponses);

        long categoryId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(get(URI + "?parentId=" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("0000"))
                .andExpect(jsonPath("$.response.categories.[0].categoryId").exists());
    }
}
