package com.shop.category.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private Long categoryId;
    private String name;

    private Set<CategoryResponseDto> children = new HashSet<>();
}
