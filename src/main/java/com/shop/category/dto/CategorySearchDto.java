package com.shop.category.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategorySearchDto implements Serializable {

    private Long parentId;
}
