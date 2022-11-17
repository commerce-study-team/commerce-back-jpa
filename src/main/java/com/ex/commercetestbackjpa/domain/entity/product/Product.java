package com.ex.commercetestbackjpa.domain.entity.product;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
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
    @Column(length = 10)
    private Long productNo;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false, length = 2)
    private String lgroup;

    @Column(nullable = false, length = 2)
    private String mgroup;

    @Column(nullable = false, length = 2)
    private String sgroup;

    @Column(nullable = false, length = 2)
    @ColumnDefault("'00'")
    private String saleFlag;

    @Column(nullable = false, length = 500)
    private String keyword;

    @Column(nullable = false, length = 100)
    @ColumnDefault("0")
    private int maxBuy;

    @Column(nullable = false)
    @ColumnDefault("0")
    private long commentCount;

    @Column(nullable = false, length = 2)
    @ColumnDefault("'00'")
    private String signFlag;

    @BatchSize(size=10)
    @OneToMany(mappedBy = "product")
    private List<ProductDT> productDtList = new ArrayList<>();

    @BatchSize(size=10)
    @OneToMany(mappedBy = "product")
    private List<ProductPrice> productPriceList = new ArrayList<>();

    @BatchSize(size=10)
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImageList = new ArrayList<>();

    @Transient
    private ProductPrice productPrice;

    @Builder
    private Product (String productName, String lgroup, String mgroup, String sgroup, String keyword) {

        this.productName = productName;
        this.lgroup = lgroup;
        this.mgroup = mgroup;
        this.sgroup = sgroup;
        this.keyword = keyword;
    }

    public void updateProductName(String productName) {
        this.productName = productName;
    }

    public void updateLMSgroup(String lgroup, String mgroup, String sgroup) {
        this.lgroup = lgroup;
        this.mgroup = mgroup;
        this.sgroup = sgroup;
    }

    public void updateMaxBuy(int maxBuy) {
        this.maxBuy = maxBuy;
    }

    public void updateKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void updateSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag;
    }

    public void updateSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }

    public void addApplyProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }
}
