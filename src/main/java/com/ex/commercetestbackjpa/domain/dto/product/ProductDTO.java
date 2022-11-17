package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductImage;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

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

        private String signFlag;

        @NotBlank(message = "검색어는 필수 입력값입니다.")
        private String keyword;

        // 단품 정보 DTO
        @NotBlank(message = "단품정보는 필수 입력값입니다.")
        private List<ProductDtDTO.Request> productDtRequestDtoList;

        // 가격 정보 DTO
        @NotBlank(message = "가격정보는 필수 입력값입니다.")
        private List<ProductPriceDTO.Request> productPriceRequestDtoList;

        private List<ProductImageDTO.Request> productImageRequestDtoList;

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

        private String signFlag;

        private String keyword;
        private int maxBuy;
        private long commentCount;

        private ProductPriceDTO.Response productPriceResponseDto;

        private List<ProductDtDTO.Response> productDtResponseDtoList;

        private List<ProductPriceDTO.Response> productPriceResponseDtoList;

        private List<ProductImageDTO.Response> productImageResponseDtoList;

        public Response (Product product) {
            this.productNo = product.getProductNo();
            this.productName = product.getProductName();
            this.lgroup = product.getLgroup();
            this.mgroup = product.getMgroup();
            this.sgroup = product.getSgroup();
            this.saleFlag = product.getSaleFlag();
            this.signFlag = product.getSignFlag();
            this.keyword = product.getKeyword();
            this.maxBuy = product.getMaxBuy();
            this.commentCount = product.getCommentCount();
        }
    }

}
