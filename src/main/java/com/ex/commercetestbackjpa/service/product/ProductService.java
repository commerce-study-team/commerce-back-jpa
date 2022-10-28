package com.ex.commercetestbackjpa.service.product;

import com.ex.commercetestbackjpa.domain.entity.product.dto.ProductRequestDto;
import com.ex.commercetestbackjpa.domain.entity.product.dto.ProductResponseDto;

import java.util.HashMap;

public interface ProductService {

    public Long saveProduct(ProductRequestDto productRequestDto);

    public ProductResponseDto findProductByProductNo(Long productNo);

    public HashMap<String, Object> findProductAll();
}
