package com.shop.category.service;

import com.shop.category.dto.CategorySaveDto;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.dto.CategoryUpdateDto;
import com.shop.category.entity.Category;
import com.shop.category.repository.CategoryQueryRepository;
import com.shop.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("데이터가 존재하지 않습니다."));
    }

    public List<Category> getCategorys(CategorySearchDto dto) {
        return categoryQueryRepository.findBySearchDto(dto);
    }

    private void print(List<Category> children) {
        for (Category child: children) {
            System.out.println("inner name:" + child.getName());
            print(child.getChildren());
        }
    }

    @Transactional
    public Long save(CategorySaveDto dto) {
        Category category = Category.createCategory(dto);
        categoryRepository.save(category);
        return category.getId();
    }

    @Transactional
    public void delete(Long categoryId) {
        Category category = getCategory(categoryId);
        category.delete();
    }

    @Transactional
    public void update(CategoryUpdateDto dto) {
        Category category = getCategory(dto.getId());
        category.update(dto);
    }
}
