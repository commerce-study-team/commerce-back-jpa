package com.ex.commercetestbackjpa.repository.product;

import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDtRepository extends JpaRepository <ProductDT, Long> {
}
