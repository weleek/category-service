package com.shop.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;

    private List<CategoryUpdateDto> children = new ArrayList<>();
}
