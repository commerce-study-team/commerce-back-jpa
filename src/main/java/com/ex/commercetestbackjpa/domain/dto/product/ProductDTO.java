package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ProductDTO {

    @Getter
    @Setter
    public static class Request {

        private Long productNo;

        @NotBlank(message = "상품명은 필수 입력값입니다.")
        private String productName;

        @NotBlank(message = "대분류는 필수 입력값입니다.")
        private String lgroup;

        @NotBlank(message = "중분류는 필수 입력값입니다.")
        private String mgroup;

        @NotBlank(message = "소분류는 필수 입력값입니다.")
        private String sgroup;

        private String saleFlag;

        @NotBlank(message = "검색어는 필수 입력값입니다.")
        private String keyword;

        // 단품 정보 DTO
        @NotBlank(message = "단품정보는 필수 입력값입니다.")
        private List<ProductDtDTO.Request> productDtRequestDtoList;

        // 가격 정보 DTO
        @NotBlank(message = "가격정보는 필수 입력값입니다.")
        private List<ProductPriceDTO.Request> productPriceRequestDtoList;

        public Product toEntity() {

            return Product.builder()
                    .productName(this.productName)
                    .lgroup(this.lgroup)
                    .mgroup(this.mgroup)
                    .sgroup(this.sgroup)
                    .keyword(this.keyword)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Response {

        private Long productNo;
        private String productName;
        private String lgroup;
        private String mgroup;
        private String sgroup;
        private String saleFlag;
        private String keyword;
        private int maxBuy;
        private long commentCount;

        private List<ProductDtDTO.Response> productDtResponseDtoList;

        private List<ProductPriceDTO.Response> productPriceResponseDtoList;

        public Response (Product product) {
            this.productNo = product.getProductNo();
            this.productName = product.getProductName();
            this.lgroup = product.getLgroup();
            this.mgroup = product.getMgroup();
            this.sgroup = product.getSgroup();
            this.saleFlag = product.getSaleFlag();
            this.keyword = product.getKeyword();
            this.maxBuy = product.getMaxBuy();
            this.commentCount = product.getCommentCount();
        }

    }

}
