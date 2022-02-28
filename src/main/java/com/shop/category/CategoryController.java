package com.shop.category;

import com.shop.category.dto.CategoryResponseDto;
import com.shop.category.dto.CategorySaveDto;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.dto.CategoryUpdateDto;
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

//    @GetMapping
//    public ResponseEntity<?> getCategorys(CategorySearchDto dto) {
//        List<CategoryResponseDto> categorys = categoryService.getCategorys(dto);
//        return ResponseEntity.ok(categorys);
//    }

    @GetMapping
    public ResponseEntity<?> getCategorys(CategorySearchDto dto) {
        List<Category> categorys = categoryService.getCategorys(dto);
        return ResponseEntity.ok(categorys);
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
