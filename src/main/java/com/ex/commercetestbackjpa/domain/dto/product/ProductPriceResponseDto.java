package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductPriceResponseDto {

    private Long productPriceNo;

    private LocalDateTime applyDate;

    private Long salePrice;

    private Long costPrice;

    private Long margin;

    private Boolean useYn;

    public ProductPriceResponseDto(ProductPrice productPrice) {
        this.productPriceNo = productPrice.getProductPriceNo();
        this.applyDate = productPrice.getApplyDate();
        this.salePrice = productPrice.getSalePrice();
        this.costPrice = productPrice.getCostPrice();
        this.margin = productPrice.getMargin();
        this.useYn = productPrice.getUseYn();
    }

}
