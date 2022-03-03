package com.shop.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop.category.entity.Category;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriesResponseDto {

    private List<CategoryResponseDto> categories;
}
