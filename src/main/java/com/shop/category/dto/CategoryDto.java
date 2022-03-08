package com.shop.category.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryDto {

    private Long id;

    @Length(min = 2, max = 20)
    private String name;

    private List<CategoryDto> children = new ArrayList<>();

    @Builder
    public CategoryDto(Long id, String name, List<CategoryDto> children) {
        this.id = id;
        this.name = name;
        this.children = children != null ? children : new ArrayList<>();
    }
}
