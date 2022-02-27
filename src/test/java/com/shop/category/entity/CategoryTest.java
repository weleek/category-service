package com.shop.category.entity;

import com.shop.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 카테고리_생성() {
        Category parent = Category.builder()
                .id(1L)
                .name("부모")
                .level(1)
                .build();

        categoryRepository.save(parent);
    }
}
