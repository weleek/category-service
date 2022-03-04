package com.shop.category.controller;

import com.shop.category.dto.*;
import com.shop.category.entity.Category;
import com.shop.category.entity.CategoryCTE;
import com.shop.category.service.CategoryService;
import com.shop.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategories(CategorySearchDto dto) {
        List<CategoryCTE> categories = categoryService.getCategories(dto);
        return ApiResult.success(categories);
//        return ApiResult.success(CategoriesResponseDto.from(categories));
    }

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody CategoryDto dto) {
        Long categoryId = categoryService.save(dto);
        return ApiResult.success(new CategoryResponseDto(categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return ApiResult.success();
    }

    @PatchMapping
    public ResponseEntity<?> patchCategory(@RequestBody CategoryDto dto) {
        categoryService.update(dto);
        return ApiResult.success();
    }
}
