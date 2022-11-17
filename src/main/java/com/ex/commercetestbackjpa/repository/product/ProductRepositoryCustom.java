package com.ex.commercetestbackjpa.repository.product;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductRepositoryCustom {

    public Page<Product> findByFilters(Map<String, String> filterMap, Pageable pageable);
}
