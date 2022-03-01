package com.shop.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.category.entity.Category;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriesResponseDto {

    private List<CategoryResponseDto> categories;

    private void addCategory(CategoryResponseDto categoryResponseDto) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        this.categories.add(categoryResponseDto);
    }

    public static CategoriesResponseDto from(List<Category> categories) {
        CategoriesResponseDto categoriesResponseDto = new CategoriesResponseDto();
        for (Category category: categories) {
            categoriesResponseDto.addCategory(CategoryResponseDto.from(category));
        }
        return categoriesResponseDto;
    }
}
