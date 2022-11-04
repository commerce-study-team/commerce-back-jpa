package com.ex.commercetestbackjpa.service.product;

import com.ex.commercetestbackjpa.domain.dto.product.*;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import com.ex.commercetestbackjpa.repository.product.ProductDtRepository;
import com.ex.commercetestbackjpa.repository.product.ProductPriceRepository;
import com.ex.commercetestbackjpa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtRepository productDtRepository;

    private final ProductPriceRepository productPriceRepository;

    @Transactional
    public Long saveProduct(ProductRequestDto productRequestDto) {
        Product product = productRequestDto.toEntity();
        List<ProductDTRequestDto> productDTRequestDtoList = productRequestDto.getProductDtRequestDtos();
        List<ProductPriceRequestDto> productPriceRequestDtoList = productRequestDto.getProductPriceRequestDtos();

        Long productNo = productRepository.save(product).getProductNo();

        for(ProductDTRequestDto productDTRequestDto : productDTRequestDtoList) {
            productDtRepository.save(productDTRequestDto.toEntity(product));
        }

        for(ProductPriceRequestDto productPriceRequestDto : productPriceRequestDtoList) {
            productPriceRepository.save(productPriceRequestDto.toEntity(product));
        }

        return productNo;
    }

    @Transactional
    public ProductResponseDto findProductByProductNo(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(NoSuchElementException::new);
        ProductResponseDto productResponseDto = new ProductResponseDto(product);
        List<ProductDtResponseDto> productDtResponseDtoList = new ArrayList<>();
        List<ProductPriceResponseDto> productPriceResponseDtoList = new ArrayList<>();
        List<ProductDT> productDTList = product.getProductDtList();
        List<ProductPrice> productPriceList = product.getProductPriceList();

        // 단품 add
        for (ProductDT productDT : productDTList) {
            productDtResponseDtoList.add(new ProductDtResponseDto(productDT));
        }

        productResponseDto.setProductDtResponseDtoList(productDtResponseDtoList);

        // 가격 add
        for (ProductPrice productPrice : productPriceList) {
            productPriceResponseDtoList.add(new ProductPriceResponseDto(productPrice));
        }

        productResponseDto.setProductPriceResponseDtoList(productPriceResponseDtoList);

        return productResponseDto;
    }

    public Map<String, Object> findProductByProductName(String productName) {
        Map<String, Object> result = new HashMap<>();
        List<ProductResponseDto> list = productRepository.findByProductName(productName).stream().map(ProductResponseDto::new).collect(Collectors.toList());

        result.put("RESULT", list);
        return result;
    }

    public HashMap<String, Object> findProductAll() {
        HashMap<String, Object> resultMap = new HashMap<>();

        List<ProductResponseDto> list = productRepository.findAll().stream().map(ProductResponseDto::new).collect(Collectors.toList());
        resultMap.put("RESULT", list);

        return resultMap;
    }

    // 단품 색상 변경
    @Transactional
    public Long updateProductDtColor(ProductDTRequestDto productDTRequestDto) {
        ProductDT productDt = productDtRepository.findById(productDTRequestDto.getProductDtNo()).orElseThrow(NoSuchElementException::new);

        productDt.updateColor(productDTRequestDto.getColorCode(), productDTRequestDto.getColorName());

        return 1L;
    }

    @Transactional
    public Long updateProductDtSize(ProductDTRequestDto productDTRequestDto) {
        ProductDT productDt = productDtRepository.findById(productDTRequestDto.getProductDtNo()).orElseThrow(NoSuchElementException::new);

        productDt.updateSize(productDTRequestDto.getSizeCode(), productDTRequestDto.getSizeName());

        return 1L;
    }

    public ProductDtResponseDto findProductDtByProductDtNo(Long productDtNo) {
        ProductDT productDT = productDtRepository.findById(productDtNo).get();

        return new ProductDtResponseDto(productDT);
    }


}
