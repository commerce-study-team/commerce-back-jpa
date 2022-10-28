package com.ex.commercetestbackjpa.domain.entity.product.dto;

import com.ex.commercetestbackjpa.domain.entity.customer.Customer;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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

    public Product toEntity(){
        return Product.SaveProduct(productName, lgroup, mgroup, sgroup, keyword);
    }
}
