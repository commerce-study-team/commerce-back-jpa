package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ProductPriceDTO {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "적용일자는 필수 입력값입니다.")
        private LocalDateTime applyDate;

        @NotBlank(message = "판매가는 필수 입력값입니다.")
        private Long salePrice;

        @NotBlank(message = "원가는 필수 입력값입니다.")
        private Long costPrice;

        @NotBlank(message = "마진액는 필수 입력값입니다.")
        private Long margin;

        private Boolean useYn;

        public ProductPrice toEntity(Product product) {
            return ProductPrice.builder()
                    .applyDate(this.applyDate)
                    .salePrice(this.salePrice)
                    .costPrice(this.costPrice)
                    .margin(this.margin)
                    .product(product)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Response {

        private Long productPriceNo;

        private LocalDateTime applyDate;

        private Long salePrice;

        private Long costPrice;

        private Long margin;

        private Boolean useYn;

        public Response(ProductPrice productPrice) {
            this.productPriceNo = productPrice.getProductPriceNo();
            this.applyDate = productPrice.getApplyDate();
            this.salePrice = productPrice.getSalePrice();
            this.costPrice = productPrice.getCostPrice();
            this.margin = productPrice.getMargin();
            this.useYn = productPrice.getUseYn();
        }

    }
}
