package com.shop.category.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.category.dto.CategoryResponseDto;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.entity.QCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<CategoryResponseDto> findBySearchDto(CategorySearchDto dto) {
        QCategory category = QCategory.category;
        QCategory parentCategory = new QCategory("parent");
        return queryFactory
                .select(Projections.fields(
                        CategoryResponseDto.class,
                        parentCategory.id.as("parentId"),
                        parentCategory.name.as("parentName"),
                        category.id.as("categoryId"),
                        category.name,
                        category.isDelete,
                        category.createdAt,
                        category.updatedAt
                        ))
                .from(category)
                .leftJoin(parentCategory)
                .on(category.parent.id.eq(parentCategory.id))
                .where(eqParentId(dto.getParentId()))
                .fetch();
    }

    private BooleanExpression eqParentId(Long parentId) {
        return parentId != null ?
                QCategory.category.parent.id.eq(parentId) : null;
    }
}
