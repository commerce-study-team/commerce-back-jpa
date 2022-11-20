package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.dto.comment.CommentDTO;
import com.ex.commercetestbackjpa.domain.entity.comment.Comment;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductImage;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ProductDTO {

    @Getter
    @Setter
    public static class Request {

        private Long productNo;

        @ApiModelProperty(value = "상품명")
        @NotBlank(message = "상품명은 필수 입력값입니다.")
        private String productName;

        @ApiModelProperty(value = "대분류")
        @NotBlank(message = "대분류는 필수 입력값입니다.")
        private String lgroup;

        @ApiModelProperty(value = "중분류")
        @NotBlank(message = "중분류는 필수 입력값입니다.")
        private String mgroup;

        @ApiModelProperty(value = "소분류")
        @NotBlank(message = "소분류는 필수 입력값입니다.")
        private String sgroup;

        @ApiModelProperty(value = "판매구분")
        private String saleFlag;

        @ApiModelProperty(value = "승인구분")
        private String signFlag;

        @ApiModelProperty(value = "1일 최대판매가능수량")
        private int maxBuy;

        @ApiModelProperty(value = "검색키워드")
        @NotBlank(message = "검색어는 필수 입력값입니다.")
        private String keyword;

        @ApiModelProperty(value = "단품정보")
        @NotBlank(message = "단품정보는 필수 입력값입니다.")
        private List<ProductDtDTO.Request> productDtRequestDtoList;

        @ApiModelProperty(value = "가격정보")
        @NotBlank(message = "가격정보는 필수 입력값입니다.")
        private List<ProductPriceDTO.Request> productPriceRequestDtoList;

        @ApiModelProperty(value = "이미지정보")
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

        private List<CommentDTO.Response> productCommentResponseDtoList;

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

        // 단품정보 세팅
        public void addProductDtDtos(List<ProductDT> productDts) {
                this.productDtResponseDtoList = productDts.stream().map(n -> new ProductDtDTO.Response(n)).collect(Collectors.toList());
        }

        // 가격정보 세팅
        public void addCurrentProductPrice(List<ProductPrice> productPrices) {
            this.productPriceResponseDto = new ProductPriceDTO.Response((productPrices.stream()
                    .filter(n -> n.getUseYn() == true)
                    .filter(n -> n.getApplyDate().isBefore(LocalDateTime.now()))
                    .max(Comparator.comparing(ProductPrice::getApplyDate))
                    .orElseThrow(() -> new NoSuchElementException("가격 정보를 찾을 수 없습니다."))));
        }

        // 이미지정보 세팅
        public void addProductImageDtos(List<ProductImage> productImages) {
            this.productImageResponseDtoList = productImages.stream().map(n -> new ProductImageDTO.Response(n)).collect(Collectors.toList());
        }

        // 상품평 세팅
        public void addProductCommentDtos(List<Comment> productComment) {
            this.productCommentResponseDtoList = productComment.stream().map(n -> new CommentDTO.Response(n)).collect(Collectors.toList());
        }
    }

}
