package com.ex.commercetestbackjpa.productTest.repository;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.dto.product.ProductRequestDto;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import com.ex.commercetestbackjpa.repository.product.ProductDtRepository;
import com.ex.commercetestbackjpa.repository.product.ProductPriceRepository;
import com.ex.commercetestbackjpa.repository.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDtRepository productDtRepository;
    @Autowired
    ProductPriceRepository productPriceRepository;

    @Test
    public void saveProductTest() {
        ProductRequestDto productRequestDto = new ProductRequestDto();

        productRequestDto.setProductName("테스트 상품");
        productRequestDto.setKeyword("테스트");
        productRequestDto.setLgroup("10");
        productRequestDto.setMgroup("10");
        productRequestDto.setSgroup("10");

        Product product = productRequestDto.toEntity();

        productRepository.save(productRequestDto.toEntity());
    }
}
