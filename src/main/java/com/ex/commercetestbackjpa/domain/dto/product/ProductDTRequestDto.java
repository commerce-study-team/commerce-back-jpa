package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTRequestDto {

        private Long productDtNo;
        private String productDtName;

        private String colorCode;

        private String colorName;

        private String sizeCode;

        private String sizeName;

        private String saleFlag;

        private String image;

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
