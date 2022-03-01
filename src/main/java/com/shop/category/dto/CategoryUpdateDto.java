package com.shop.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {

    @NotNull
    private Long id;

    @NotEmpty
    private String name;

    private List<CategoryUpdateDto> children = new ArrayList<>();
}
