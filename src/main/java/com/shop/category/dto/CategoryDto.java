package com.shop.category.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryDto {

    private Long id;

    private String name;

    private List<CategoryDto> children = new ArrayList<>();

    @Builder
    public CategoryDto(Long id, String name, List<CategoryDto> children) {
        this.id = id;
        this.name = name;

        if (children == null) {
            this.children = new ArrayList<>();
        }
        else {
            this.children = children;
        }
    }
}
