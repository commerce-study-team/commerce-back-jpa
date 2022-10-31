package com.ex.commercetestbackjpa.domain.dto.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class ProductRequestDto {

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

    private List<ProductDTRequestDto> productDtRequestDtos;

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
