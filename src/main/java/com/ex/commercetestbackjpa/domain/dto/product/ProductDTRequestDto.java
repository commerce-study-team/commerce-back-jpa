package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;

@Getter
@Setter
public class ProductDTRequestDto {

        private String productNameDt;

        @Column(length = 2)
        private String colorCode;

        @Column(length = 100)
        private String colorName;

        @Column(length = 2)
        private String sizeCode;

        @Column(length = 100)
        private String sizeName;

        @Column(nullable = false, length = 2)
        @ColumnDefault("00")
        private String saleFlag;

        @Column(length = 200)
        private String image;

        public ProductDT toEntity(Product product) {
            return ProductDT.builder()
                  .productNameDt(this.productNameDt)
                  .colorCode(this.colorCode)
                  .colorName(this.colorName)
                  .sizeCode(this.sizeCode)
                  .sizeName(this.sizeName)
                  .image(this.image)
                  .product(product)
                  .build();
        }
}
