package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTRequestDto {

        private Long productDtNo;

        @ApiModelProperty(value = "단품명", example = "base", required = true)
        private String productDtName;

        @ApiModelProperty(value = "색상코드", example = "10", required = true)
        private String colorCode;

        private String colorName;

        @ApiModelProperty(value = "사이즈코드", example = "10", required = true)
        private String sizeCode;

        private String sizeName;

        private String saleFlag;

        private String image;

        @ApiModelProperty(value = "상품코드", example = "1L", required = true)
        private Long ProductNo;

        public ProductDT toEntity(Product product) {
            return ProductDT.builder()
                  .productDtName(this.productDtName)
                  .colorCode(this.colorCode)
                  .colorName(this.colorName)
                  .sizeCode(this.sizeCode)
                  .sizeName(this.sizeName)
                  .image(this.image)
                  .product(product)
                  .build();
        }
}
