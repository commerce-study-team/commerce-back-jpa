package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductImage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class ProductImageDTO {

    @Getter
    @Setter
    public static class Request {

        private Long productImageNo;

        @ApiModelProperty(value = "이미지명", required = true)
        @NotBlank(message = "이미지명는 필수 입력값입니다.")
        private String imageName;

        @ApiModelProperty(value = "실제 이미지명", required = true)
        @NotBlank(message = "이미지명는 필수 입력값입니다.")
        private String imageRealName;

        @ApiModelProperty(value = "사용여부", example = "base")
        private Boolean useYn;

        private MultipartFile imgFile;

        public ProductImage toEntity() {
            return ProductImage.builder()
                    .imageRealName(this.imageRealName)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Response {

        private Long productImageNo;

        private String imageName;

        private String imageRealName;

        private Boolean useYn;

        public Response(ProductImage productImage) {
            this.productImageNo = productImage.getProductImageNo();
            this.imageName = productImage.getImageName();
            this.imageRealName = productImage.getImageRealName();
            this.useYn = productImage.getUseYn();
        }
    }
}
