package com.shop.category.entity;

import com.shop.category.dto.CategorySaveDto;
import com.shop.category.dto.CategoryUpdateDto;
import com.shop.common.converter.BooleanToYNConverter;
import com.shop.common.model.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;

@Slf4j
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@org.hibernate.annotations.Table(appliesTo = "category", comment = "카테고리 정보")
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "category_id")
    @Comment("카테고리시스템아이디")
    private Long id;

    @Column(nullable = false, length = 20)
    @Comment("카테고리명")
    @Length(min = 2, max = 20)
    private String name;

    @Column(columnDefinition = "varchar(1) not null default 'N'", nullable = false)
    @Comment("카테고리 삭제 여부")
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isDelete;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "category_id")
    @ToString.Exclude
    private Category parent;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "parent")
    @ToString.Exclude
    private List<Category> children = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Builder
    public Category(Long id, String name, List<Category> children) {
        this.id = id;
        this.name = name;
        this.isDelete = false;

        if (children == null) {
            this.children = new ArrayList<>();
        }
        else {
            this.children = children;
        }
    }

    void setParent(Category parent) {
        this.parent = parent;
    }

    private void changeName(String name) {
        this.name = name;
    }

    public void addChild(Category children) {
        if (children.getParent() != this) {
            children.setParent(this);
        }
        this.children.add(children);
    }

    private void setChildren(List<CategorySaveDto> childrenDto) {
        for (CategorySaveDto childDto: childrenDto) {
            Category category = Category.builder()
                    .name(childDto.getName())
                    .build();

            category.setParent(this);
            this.addChild(category);
            if (childDto.getChildren() != null) {
                category.setChildren(childDto.getChildren());
            }
        }
    }

    private void updateChindren(List<CategoryUpdateDto> childrenDto) {
        for (Category child: this.getChildren()) {
            for (CategoryUpdateDto childDto: childrenDto) {
                if (child.getId().equals(childDto.getId())) {
                    child.changeName(childDto.getName());
                    if (childDto.getChildren() != null) {
                        child.updateChindren(childDto.getChildren());
                    }
                }
            }
        }
    }

    public static Category createCategory(CategorySaveDto dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .build();

        category.setChildren(dto.getChildren());
        return category;
    }

    public void delete() {
        if (isDelete) {
            throw new IllegalStateException(id + "는 이미 삭제된 카테고리 입니다.");
        }
        this.isDelete = true;

        for (Category child: this.getChildren()) {
            child.delete();
        }
    }

    public void update(CategoryUpdateDto dto) {
        this.name = dto.getName();
        updateChindren(dto.getChildren());
    }
}
