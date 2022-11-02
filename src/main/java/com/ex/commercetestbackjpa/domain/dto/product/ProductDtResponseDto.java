package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDtResponseDto {

    private Long productDtNo;

    private String productDtName;

    private String colorCode;

    private String colorName;

    private String sizeCode;

    private String sizeName;

    private String saleFlag;

    private String image;

    public ProductDtResponseDto(ProductDT productDt) {
        this.productDtNo = productDt.getProductDtNo();
        this.productDtName = productDt.getProductDtName();
        this.colorCode =  productDt.getColorCode();
        this.colorName = productDt.getColorName();
        this.sizeCode = productDt.getSizeCode();
        this.sizeName = productDt.getSizeName();
        this.saleFlag = productDt.getSaleFlag();
        this.image = productDt.getImage();
    }

}
