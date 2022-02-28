package com.shop.category.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.category.dto.CategoryResponseDto;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.entity.Category;
import com.shop.category.entity.QCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.dsl.Expressions.list;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Category> findBySearchDto(CategorySearchDto dto) {
        QCategory category = QCategory.category;

        List<Category> categories = queryFactory
                .select(category)
                .from(category)
                .leftJoin(category.children, category)
                .fetchJoin()
                .where(existsId(dto.getCategoryId()))
                .fetch();

        for (Category c: categories) {
            System.out.println("name:" + c.getName());
        }

        return new ArrayList<>();
//        return categories.stream()
//                .map(parent -> Category.builder()
//                        .name(parent.getName())
//                        .children(parent.getChildren())
//                        .build())
//                .collect(Collectors.toList());
    }

    private BooleanExpression existsId(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return QCategory.category.parent.id.eq(categoryId);
    }
}
