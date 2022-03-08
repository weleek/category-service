package com.shop.category.controller;

import com.shop.category.dto.*;
import com.shop.category.service.CategoryService;
import com.shop.common.response.ApiResponse;
import com.shop.exception.ApiException;
import com.shop.exception.ExceptionDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<?> getCategories(CategorySearchDto dto) {
        List<CategoryResponseDto> categories = categoryService.getCategories(dto);
        return ApiResponse.success(CategoriesResponseDto.builder()
                .categories(categories)
                .build());
    }

    @PostMapping
    public ApiResponse<?> postCategory(@RequestBody CategoryDto dto) {
        Long categoryId = categoryService.save(dto);
        return ApiResponse.success(CategoryResponseDto.builder()
                .categoryId(categoryId)
                .build());
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return ApiResponse.success();
    }

    @PatchMapping
    public ApiResponse<?> patchCategory(@RequestBody CategoryDto dto) {
        categoryService.update(dto);
        return ApiResponse.success();
    }
}
