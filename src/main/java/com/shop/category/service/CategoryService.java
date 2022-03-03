package com.shop.category.service;

import com.shop.category.dto.CategoryDto;
import com.shop.category.dto.CategoryResponseDto;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.entity.Category;
import com.shop.category.repository.CategoryQueryRepository;
import com.shop.category.repository.CategoryRepository;
import com.shop.exception.DataNotFoundException;
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

    public List<CategoryResponseDto> getCategories(CategorySearchDto dto) {
        return categoryQueryRepository.findBySearchDto(dto);
    }

    @Transactional
    public Long save(CategoryDto dto) {
        Category category;
        if (dto.getId() != null) {
            category = categoryRepository.findById(dto.getId())
                    .orElseThrow(DataNotFoundException::new);

            for (CategoryDto child: dto.getChildren()) {
                category.addChild(Category.createCategory(child));
            }
        }
        else {
            category = Category.createCategory(dto);
        }

        categoryRepository.save(category);
        return category.getId();
    }

    @Transactional
    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(DataNotFoundException::new);
        category.delete();
    }

    @Transactional
    public void update(CategoryDto dto) {
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(DataNotFoundException::new);
        category.update(dto);
    }
}
