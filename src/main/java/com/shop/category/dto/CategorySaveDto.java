package com.shop.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveDto {

    @NotEmpty
    private String name;

    private List<CategorySaveDto> children = new ArrayList<>();
}
