package com.ex.commercetestbackjpa.domain.entity.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity(name="tproductdt")
@Getter
@NoArgsConstructor
@DynamicInsert
public class ProductDT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNoDt;

    @Column(nullable = false, length = 100)
    private String productNameDt;

    @Column(length = 2)
    private String colorCode;

    @Column(length = 100)
    private String colorName;

    @Column(length = 2)
    private String sizeCode;

    @Column(length = 100)
    private String sizeName;

    @Column(nullable = false, length = 2)
    @ColumnDefault("00")
    private String saleFlag;

    @Column(length = 200)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    public ProductDT (String productNameDt, String colorCode, String colorName, String sizeCode, String sizeName,
                      String saleFlag, String image, Product product) {

        this.productNameDt = productNameDt;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.sizeCode = sizeCode;
        this.sizeName = sizeName;
        this.saleFlag = saleFlag;
        this.image = image;
        this.product = product;
    }
}
