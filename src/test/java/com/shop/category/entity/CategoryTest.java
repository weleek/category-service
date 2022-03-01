package com.shop.category.entity;

import com.shop.category.dto.CategorySaveDto;
import com.shop.category.dto.CategoryUpdateDto;
import com.shop.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 카테고리_생성() {
        // given
        List<CategorySaveDto> small = Collections.singletonList(new CategorySaveDto("소분류", new ArrayList<>()));
        List<CategorySaveDto> medium = Collections.singletonList(new CategorySaveDto("중분류", small));
        CategorySaveDto dto = new CategorySaveDto("대분류", medium);

        // when
        Category category = Category.createCategory(dto);
        categoryRepository.save(category);

        Category findCategory = categoryRepository.findById(category.getId()).get();

        // then
        assertThat(findCategory).isEqualTo(category);

        assertThat(category.getName()).isEqualTo("대분류");
        assertThat(category.getChildren().get(0).getName()).isEqualTo("중분류");
        assertThat(category.getChildren().get(0).getChildren().get(0).getName()).isEqualTo("소분류");
    }

    @Test
    void 카테고리_수정() {
        // given
        CategorySaveDto dto = new CategorySaveDto("분류", new ArrayList<>());
        CategoryUpdateDto updateDto = new CategoryUpdateDto(1L, "분류변경", new ArrayList<>());

        Category category = Category.createCategory(dto);
        categoryRepository.save(category);

        // when
        category.update(updateDto);
        categoryRepository.save(category);

        Category findCategory = categoryRepository.findById(category.getId()).get();

        // then
        assertThat(findCategory.getName()).isEqualTo("분류변경");
    }

    @Test
    void 카테고리_삭제() {
        // given
        CategorySaveDto dto = new CategorySaveDto("분류", new ArrayList<>());

        Category category = Category.createCategory(dto);
        categoryRepository.save(category);

        // when
        category.delete();
        categoryRepository.save(category);

        Category findCategory = categoryRepository.findById(category.getId()).get();

        // then
        assertThat(findCategory.getIsDelete()).isEqualTo(true);
    }
    
    @ParameterizedTest
    @CsvSource(value = {"1:대분류", "2:중분류", "3:소분류"}, delimiter = ':')
    void 카테고리_조회(Long input, String expected) {
        // given
        List<CategorySaveDto> small = Collections.singletonList(new CategorySaveDto("소분류", new ArrayList<>()));
        List<CategorySaveDto> medium = Collections.singletonList(new CategorySaveDto("중분류", small));
        CategorySaveDto dto = new CategorySaveDto("대분류", medium);

        // when
        Category category = Category.createCategory(dto);
        categoryRepository.save(category);
        
        Category findCategory = categoryRepository.findById(input).get();
        
        // then
        assertThat(findCategory.getName()).isEqualTo(expected);
    }
}
