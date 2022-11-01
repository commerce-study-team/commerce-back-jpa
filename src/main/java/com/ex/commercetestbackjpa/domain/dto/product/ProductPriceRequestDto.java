package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductPriceRequestDto {

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
