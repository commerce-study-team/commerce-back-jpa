package com.ex.commercetestbackjpa.repository.product;

import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository <ProductPrice, Long> {

}
