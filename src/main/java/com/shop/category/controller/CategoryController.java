package com.shop.category.controller;

import com.shop.category.dto.*;
import com.shop.category.entity.Category;
import com.shop.category.service.CategoryService;
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
    public ResponseEntity<?> getCategorys(CategorySearchDto dto) {
        List<Category> categories = categoryService.getCategories(dto);
        return ResponseEntity.ok(CategoriesResponseDto.from(categories));
    }

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody CategorySaveDto dto) {
        Long categoryId = categoryService.save(dto);
        return ResponseEntity.ok(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok("success");
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> putCategory(@PathVariable("categoryId") Long categoryId,
                                         @RequestBody CategoryUpdateDto dto) {
        categoryService.update(dto);
        return ResponseEntity.ok("success");
    }
}
