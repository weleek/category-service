package com.shop.category.entity;

import com.shop.common.model.BaseEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Getter
@Entity
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@org.hibernate.annotations.Table(appliesTo = "category", comment = "카테고리 정보")
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "category_id")
    @Comment("카테고리시스템아이디")
    private Long id;

    @Column(nullable = false)
    @Comment("분류 단계")
    private int level;

    @Column(unique = true, nullable = false, length = 20)
    @Comment("카테고리명")
    private String name;

    @Column(nullable = false)
    @ColumnDefault("1")
    @Comment("카테고리 사용 여부")
    private Boolean isUse;

    @ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "category_id")
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  mappedBy = "parent")
    private List<Category> children;

    @Builder
    public Category(Long id, int level, String name) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.isUse = true;
    }

}
