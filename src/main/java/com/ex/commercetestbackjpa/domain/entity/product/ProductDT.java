package com.ex.commercetestbackjpa.domain.entity.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity(name="TPRODUCTDT")
@Getter
@NoArgsConstructor
@DynamicInsert
public class ProductDT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productDtNo;

    @Column(length = 100)
    @ColumnDefault("단품기본")
    private String productDtName;

    @Column(length = 2)
    @ColumnDefault("10")
    private String colorCode;

    @Column(length = 100)
    @ColumnDefault("기본")
    private String colorName;

    @Column(length = 2)
    @ColumnDefault("10")
    private String sizeCode;

    @Column(length = 100)
    @ColumnDefault("기본")
    private String sizeName;

    @Column(nullable = false, length = 2)
    @ColumnDefault("00")
    private String saleFlag;

    @Column(length = 200)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    private ProductDT (String productDtName, String colorCode, String colorName, String sizeCode, String sizeName,
                      String saleFlag, String image, Product product) {

        this.productDtName = productDtName;
        this.colorCode = colorCode;
        this.colorName = colorName;
        this.sizeCode = sizeCode;
        this.sizeName = sizeName;
        this.saleFlag = saleFlag;
        this.image = image;
        this.product = product;
    }

    public void updateProductDtName (String productDtName) {
        this.productDtName = productDtName;

    }

    public void updateColor (String colorCode, String colorName) {
        this.colorCode = colorCode;
        this.colorName = colorName;
    }

    public void updateSize (String sizeCode, String sizeName) {
        this.sizeCode = sizeCode;
        this.sizeName = sizeName;
    }

    public void updateSaleFlag (String saleFlag) {
        this.saleFlag = saleFlag;
    }

    public void updateImage (String image) {
        this.image = image;
    }
}
