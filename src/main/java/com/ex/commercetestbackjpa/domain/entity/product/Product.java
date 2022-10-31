package com.ex.commercetestbackjpa.domain.entity.product;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="tproduct")
@Getter
@NoArgsConstructor
@DynamicInsert
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_no")
    private Long productNo;

    @Column(name="product_name", nullable = false, length = 100)
    private String productName;

    @Column(nullable = false, length = 2)
    private String lgroup;

    @Column(nullable = false, length = 2)
    private String mgroup;

    @Column(nullable = false, length = 2)
    private String sgroup;

    @Column(name="sale_flag", nullable = false, length = 2)
    @ColumnDefault("00")
    private String saleFlag;

    @Column(nullable = false, length = 500)
    private String keyword;

    @Column(name="max_buy", nullable = false, length = 100)
    @ColumnDefault("0")
    private int maxBuy;

    @Column(name="comment_count", nullable = false)
    @ColumnDefault("0")
    private long commentCount;

    @OneToMany(mappedBy = "product")
    private List<ProductDT> productDtList = new ArrayList<>();

    @Builder
    public Product (String productName, String lgroup, String mgroup, String sgroup, String keyword) {

        this.productName = productName;
        this.lgroup = lgroup;
        this.mgroup = mgroup;
        this.sgroup = sgroup;
        this.keyword = keyword;
    }
}
