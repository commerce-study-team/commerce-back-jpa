package com.ex.commercetestbackjpa.repository.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductName(String ProductName);

    List<Product> findByKeyword(String keyword);

    List<Product> findBySaleFlag(String saleFlag);

    List<Product> findBySignFlag(String signFlag);

}
