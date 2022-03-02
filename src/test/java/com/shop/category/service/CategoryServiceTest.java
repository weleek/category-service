package com.shop.category.service;

import com.shop.category.dto.CategoryDto;
import com.shop.category.entity.Category;
import com.shop.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    private CategoryDto saveDto;
    private Category category;

    @BeforeEach
    void setUp() {
        List<CategoryDto> smallDto = Collections.singletonList(CategoryDto.builder()
                .name("소분류")
                .build());

        List<CategoryDto> mediumDto = Collections.singletonList(CategoryDto.builder()
                .name("중분류")
                .children(smallDto)
                .build());

        saveDto = CategoryDto.builder()
                .name("대분류")
                .children(mediumDto)
                .build();

        List<Category> small = Collections.singletonList(Category.builder()
                .id(3L)
                .name("소분류")
                .build());

        List<Category> medium = Collections.singletonList(Category.builder()
                .id(2L)
                .name("중분류")
                .children(small)
                .build());

        category = Category.builder()
                .id(1L)
                .name("대분류")
                .children(medium)
                .build();
    }


    @Test
    void 카테고리_등록() {
        // given
        when(categoryRepository.save(any(Category.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.of(category));

        // when
        categoryService.save(saveDto);

        Category findCategory = categoryRepository.findById(1L).get();

        // then
        assertThat(findCategory.getId()).isEqualTo(1L);
    }

    @Test
    void 카테고리_수정() {
        // given
        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));

        CategoryDto dto = CategoryDto.builder()
                .id(1L)
                .name("대분류 변경")
                .build();

        // when
        categoryService.update(dto);

        Category findCategory = categoryRepository.findById(1L).get();

        // then
        assertThat(findCategory.getName()).isEqualTo("대분류 변경");
    }

    @Test
    void 카테고리_삭제() {
        // given
        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));

        // when
        categoryService.delete(category.getId());

        Category findCategory = categoryRepository.findById(category.getId()).get();

        // then
        assertThat(findCategory.getIsDelete()).isEqualTo(true);
    }

    @Test
    void 카테고리_삭제_예외() {
        // given
        Category category = Category.builder()
                .id(1L)
                .name("테스트 카테고리")
                .build();

        category.delete();

        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));

        // when
        Throwable thrown = catchThrowable(() -> categoryService.delete(category.getId()));

        // then
        assertThat(thrown)
                .isInstanceOfAny(IllegalStateException.class);
    }
}
