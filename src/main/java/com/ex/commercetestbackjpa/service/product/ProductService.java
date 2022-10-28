package com.ex.commercetestbackjpa.service.product;

import com.ex.commercetestbackjpa.domain.entity.product.dto.ProductRequestDto;
import com.ex.commercetestbackjpa.domain.entity.product.dto.ProductResponseDto;

public interface ProductService {

    public Long saveProduct(ProductRequestDto productRequestDto);

    public ProductResponseDto findProductByProductNo(Long productNo);
}
