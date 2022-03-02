package com.shop.category.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.entity.Category;
import com.shop.category.entity.QCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Category> findBySearchDto(CategorySearchDto dto) {
        QCategory category = QCategory.category;

        return queryFactory
                .select(category)
                .from(category)
                .where(eqParentId(dto.getParentId()))
                .where(category.isDelete.eq(false))
                .fetch();
    }

    private BooleanExpression eqParentId(Long parentId) {
        return parentId != null
                ? QCategory.category.parent.id.eq(parentId) : QCategory.category.parent.id.isNull();
    }
}
