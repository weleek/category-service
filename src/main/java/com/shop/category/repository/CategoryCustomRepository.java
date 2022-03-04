package com.shop.category.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shop.category.dto.CategorySearchDto;
import com.shop.category.entity.CategoryCTE;
import com.shop.category.entity.QCategory;
import com.shop.category.entity.QCategoryCTE;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryCustomRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public List<CategoryCTE> findTest(CategorySearchDto dto) {
        QCategoryCTE parentCategory = new QCategoryCTE("parent");

        return new BlazeJPAQuery<CategoryCTE>(entityManager, criteriaBuilderFactory)
                .withRecursive(QCategoryCTE.categoryCTE, new BlazeJPAQuery<>()
                        .unionAll(
                                new BlazeJPAQuery<>()
                                        .from(QCategory.category)
                                        .bind(QCategoryCTE.categoryCTE.id, QCategory.category.id)
                                        .bind(QCategoryCTE.categoryCTE.name, QCategory.category.name)
                                        .bind(QCategoryCTE.categoryCTE.parent, QCategory.category.parent)
                                        .where(QCategory.category.parent.id.isNull()),

                                new BlazeJPAQuery<>()
                                        .from(QCategory.category)
                                        .from(QCategoryCTE.categoryCTE, parentCategory)
                                        .bind(QCategoryCTE.categoryCTE.id, QCategory.category.id)
                                        .bind(QCategoryCTE.categoryCTE.name, QCategory.category.name)
                                        .bind(QCategoryCTE.categoryCTE.parent, QCategory.category.parent)
                                        .where(QCategory.category.id.eq(parentCategory.parent.id)))
                )
                .select(QCategoryCTE.categoryCTE)
                .from(QCategoryCTE.categoryCTE)
                .fetch();
    }

    private BooleanExpression eqCategoryId(Long parentId) {
        return parentId != null ? QCategory.category.id.eq(parentId) : QCategory.category.parent.isNull();
    }
}
