package com.shop.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.category.entity.Category;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDto {

    private Long categoryId;
    private String name;

    private List<CategoryResponseDto> children;

    public CategoryResponseDto(Long categoryId) {
        this.categoryId = categoryId;
    }

    private void addChild(CategoryResponseDto categoryResponseDto) {
        if (children == null) {
            children = new ArrayList<>();
        }
        this.children.add(categoryResponseDto);
    }

    public static CategoryResponseDto from(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryId(category.getId())
                .setName(category.getName());

        categoryResponseDto.setChildren(category.getChildren());
        return categoryResponseDto;
    }

    private void setChildren(List<Category> children) {
        for (Category child: children) {
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
            categoryResponseDto.setCategoryId(child.getId())
                    .setName(child.getName());

            this.addChild(categoryResponseDto);
            if (child.getChildren() != null) {
                categoryResponseDto.setChildren(child.getChildren());
            }
        }
    }
}
