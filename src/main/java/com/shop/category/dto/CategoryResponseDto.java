package com.shop.category.dto;

import com.shop.common.converter.BooleanToYNConverter;
import lombok.*;

import javax.persistence.Convert;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    private Long parentId;
    private String parentName;

    private Long categoryId;
    private String name;

    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isDelete;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
