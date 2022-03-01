package com.shop.category.controller;

import com.shop.category.dto.*;
import com.shop.category.entity.Category;
import com.shop.category.service.CategoryService;
import com.shop.common.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.HasControls;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategorys(CategorySearchDto dto) {
        List<Category> categories = categoryService.getCategories(dto);
        return ApiResult.success(CategoriesResponseDto.from(categories));
    }

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody @Valid CategorySaveDto dto) {
        Long categoryId = categoryService.save(dto);
        return ApiResult.success(new CategoryResponseDto(categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return ApiResult.success();
    }

    @PatchMapping
    public ResponseEntity<?> putCategory(@RequestBody @Valid CategoryUpdateDto dto) {
        categoryService.update(dto);
        return ApiResult.success();
    }
}
