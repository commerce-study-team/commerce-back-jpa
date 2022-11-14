package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class ProductDtDTO {

    @Getter
    @Setter
    public static class Request {

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

        public ProductDT toEntity() {
            return ProductDT.builder()
                    .productDtName(this.productDtName)
                    .colorCode(this.colorCode)
                    .colorName(this.colorName)
                    .sizeCode(this.sizeCode)
                    .sizeName(this.sizeName)
                    .image(this.image)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Response {

        private Long productDtNo;

        private String productDtName;

        private String colorCode;

        private String colorName;

        private String sizeCode;

        private String sizeName;

        private String saleFlag;

        private String image;

        public Response(ProductDT productDt) {
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

}
