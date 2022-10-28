package com.ex.commercetestbackjpa.service.product.impl;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.dto.ProductRequestDto;
import com.ex.commercetestbackjpa.domain.entity.product.dto.ProductResponseDto;
import com.ex.commercetestbackjpa.repository.product.ProductRepository;
import com.ex.commercetestbackjpa.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Long saveProduct(ProductRequestDto productRequestDto) {
        return productRepository.save((productRequestDto.toEntity())).getProductNo();
    }
    @Override
    public ProductResponseDto findProductByProductNo(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(NoSuchElementException::new);

        return new ProductResponseDto(product);
    }
}
