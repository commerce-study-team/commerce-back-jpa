package com.ex.commercetestbackjpa.domain.entity.product;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.domain.entity.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="tproduct")
@Getter
@NoArgsConstructor
@DynamicInsert
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @BatchSize(size=100)
    @OneToMany(mappedBy = "product")
    private List<ProductDT> productDtList = new ArrayList<>();

    @BatchSize(size=100)
    @OneToMany(mappedBy = "product")
    private List<ProductPrice> productPriceList = new ArrayList<>();

    @BatchSize(size=100)
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImageList = new ArrayList<>();

    @BatchSize(size=100)
    @OneToMany(mappedBy = "product")
    private List<Comment> productCommentList = new ArrayList<>();

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

    public void updateProductOptions(ProductDTO.Request productRequestDto) {
        this.updateProductName(productRequestDto.getProductName());
        this.updateLMSgroup(productRequestDto.getLgroup(), productRequestDto.getMgroup(), productRequestDto.getSgroup());
        this.updateMaxBuy(productRequestDto.getMaxBuy());
        this.updateKeyword(productRequestDto.getKeyword());
        this.updateSaleFlag(productRequestDto.getSaleFlag());
        this.updateSignFlag(productRequestDto.getSignFlag());
    }

    public void updateProductName(String productName) {
        Optional.ofNullable(productName).ifPresent(p -> this.productName = p);
    }

    public void updateLMSgroup(String lgroup, String mgroup, String sgroup) {
        Optional.ofNullable(lgroup).ifPresent(l -> this.lgroup = l);
        Optional.ofNullable(mgroup).ifPresent(m -> this.mgroup = m);
        Optional.ofNullable(sgroup).ifPresent(s -> this.sgroup = s);
    }

    public void updateMaxBuy(int maxBuy) {
        Optional.ofNullable(maxBuy).ifPresent(m -> this.maxBuy = m);
    }

    public void updateKeyword(String keyword) {
        Optional.ofNullable(keyword).ifPresent(k -> this.keyword = k);

    }

    public void updateSaleFlag(String saleFlag) {
        Optional.ofNullable(saleFlag).ifPresent(s -> this.saleFlag = s);
    }

    public void updateSignFlag(String signFlag) {
        Optional.ofNullable(signFlag).ifPresent(s -> this.signFlag = s);
    }

    public void addApplyProductPrice(ProductPrice productPrice) {
        Optional.ofNullable(productPrice).ifPresent(p -> this.productPrice = p);
    }

    public void updateCommentCount(Long commentCount) {
        Optional.ofNullable(commentCount).ifPresent(c -> this.commentCount = c);
    }
}
