package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ProductPriceDTO {

    @Getter
    @Setter
    public static class Request {

        private Long productPriceNo;

        @ApiModelProperty(value = "적용일자")
        @NotBlank(message = "적용일자는 필수 입력값입니다.")
        private LocalDateTime applyDate;

        @ApiModelProperty(value = "판매가")
        @NotBlank(message = "판매가는 필수 입력값입니다.")
        private Long salePrice;

        @ApiModelProperty(value = "원가")
        @NotBlank(message = "원가는 필수 입력값입니다.")
        private Long costPrice;

        @ApiModelProperty(value = "마진액")
        @NotBlank(message = "마진액는 필수 입력값입니다.")
        private Long margin;

        @ApiModelProperty(value = "사용여부")
        private Boolean useYn;

        public ProductPrice toEntity() {
            return ProductPrice.builder()
                    .applyDate(this.applyDate)
                    .salePrice(this.salePrice)
                    .costPrice(this.costPrice)
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

        public void updateUseYn(Boolean useYn) {
            this.useYn = useYn;
        }

    }
}
