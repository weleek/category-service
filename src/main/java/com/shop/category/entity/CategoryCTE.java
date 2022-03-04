package com.shop.category.entity;

import com.blazebit.persistence.CTE;
import com.shop.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@CTE
@Getter
@Setter
@Entity
public class CategoryCTE {

    @Id
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "category_id")
    @ToString.Exclude
    private Category parent;
}
