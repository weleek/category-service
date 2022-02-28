package com.shop.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveDto {

    @NotEmpty
    private String name;

    private Set<CategorySaveDto> children = new HashSet<>();
}
