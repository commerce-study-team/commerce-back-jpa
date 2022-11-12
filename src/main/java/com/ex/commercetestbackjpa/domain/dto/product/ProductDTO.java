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

        private List<ProductDtDTO.Response> productDtResponseDtoList = new ArrayList<>();

        private List<ProductPriceDTO.Response> productPriceResponseDtoList = new ArrayList<>();

        private List<ProductImageDTO.Response> productImageResponseDtoList = new ArrayList<>();

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

        public void addProductDtList(Product product) {
            List<ProductDT> productDTList = product.getProductDtList();

            // 단품 add
            for (ProductDT productDT : productDTList) {
                productDtResponseDtoList.add(new ProductDtDTO.Response(productDT));
            }
        }

        public void addProductPriceList(Product product) {
            List<ProductPrice> productPriceList = product.getProductPriceList();

            // 가격 add
            for (ProductPrice productPrice : productPriceList) {
                productPriceResponseDtoList.add(new ProductPriceDTO.Response(productPrice));
            }
        }

        public void findProductPrice (Product product) {
            List<ProductPrice> productPriceList = product.getProductPriceList();

            ProductPrice productPrice = productPriceList.stream()
                    .filter(n -> n.getUseYn() == true)
                    .filter(n -> n.getApplyDate().isBefore(LocalDateTime.now()))
                    .max(Comparator.comparing(ProductPrice::getApplyDate))
                    .orElseThrow(() -> new NoSuchElementException("가격 정보를 찾을 수 없습니다."));

            this.productPriceResponseDto =  new ProductPriceDTO.Response(productPrice);
        }

        public void addProductImageList(Product product) {
            List<ProductImage> productPriceList = product.getProductImageList();

            // 가격 add
            for (ProductImage productImage : productPriceList) {
                productImageResponseDtoList.add(new ProductImageDTO.Response(productImage));
            }
        }
    }

}
