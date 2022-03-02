package com.shop.category.entity;

import com.shop.category.dto.CategoryDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {

    private Category getCategory(CategoryDto dto) {
        return Category.createCategory(dto);
    }

    @Test
    void 카테고리_생성() {
        // given
        CategoryDto dto = CategoryDto.builder()
                .name("기본 분류")
                .build();

        // when
        Category category = getCategory(dto);

        // then
        assertThat(category.getName()).isEqualTo("기본 분류");
        assertThat(category.getIsDelete()).isEqualTo(false);
    }

    @Test
    void 카테고리명_수정() {
        // given
        CategoryDto dto = CategoryDto.builder()
                .name("기본 분류")
                .build();

        CategoryDto updateDto = CategoryDto.builder()
                .name("기본 분류 변경")
                .build();

        Category category = getCategory(dto);

        // when
        category.update(updateDto);

        // then
        assertThat(category.getName()).isNotEqualTo("기본 분류");
    }

    @Test
    void 카테고리_삭제() {
        // given
        CategoryDto dto = CategoryDto.builder()
                .name("기본 분류")
                .build();

        Category category = getCategory(dto);

        // then
        category.delete();

        // then
        assertThat(category.getIsDelete()).isEqualTo(true);
    }
}
