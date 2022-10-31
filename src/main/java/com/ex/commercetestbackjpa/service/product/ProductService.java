package com.ex.commercetestbackjpa.service.product;

import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDTRequestDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductRequestDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductResponseDto;
import com.ex.commercetestbackjpa.repository.product.ProductDtRepository;
import com.ex.commercetestbackjpa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtRepository productDtRepository;

    public Long saveProduct(ProductRequestDto productRequestDto) {

        Product product = productRequestDto.toEntity();
        Long productNo = productRepository.save(product).getProductNo();

        List<ProductDTRequestDto> productDTRequestDtoList = productRequestDto.getProductDtRequestDtos();

        for(ProductDTRequestDto productDTRequestDto : productDTRequestDtoList) {
            productDtRepository.save(productDTRequestDto.toEntity(product));
        }


        return productNo;
    }
    public ProductResponseDto findProductByProductNo(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(NoSuchElementException::new);

        return new ProductResponseDto();
    }

    public HashMap<String, Object> findProductAll() {
        HashMap<String, Object> resultMap = new HashMap<>();

        List<ProductResponseDto> list = productRepository.findAll().stream().map(ProductResponseDto::new).collect(Collectors.toList());
        resultMap.put("RESULT", list);

        return resultMap;

    }
}
