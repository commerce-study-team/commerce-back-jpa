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

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtRepository productDtRepository;

    private final ProductPriceRepository productPriceRepository;

    @Transactional
    public Long saveProduct(ProductDTO.Request productRequestDto) {
        Product product = productRequestDto.toEntity();
        List<ProductDtDTO.Request> productDTRequestDtoList = productRequestDto.getProductDtRequestDtoList();
        List<ProductPriceDTO.Request> productPriceRequestDtoList = productRequestDto.getProductPriceRequestDtoList();

        Long productNo = productRepository.save(product).getProductNo();

        for(ProductDtDTO.Request productDTRequestDto : productDTRequestDtoList) {
            productDtRepository.save(productDTRequestDto.toEntity(product));
        }

        for(ProductPriceDTO.Request productPriceRequestDto : productPriceRequestDtoList) {
            productPriceRepository.save(productPriceRequestDto.toEntity(product));
        }

        return productNo;
    }

    @Transactional
    public ProductDTO.Response findProductByProductNo(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("조회된 상품이 없습니다."));
        ProductDTO.Response productResponseDto = new ProductDTO.Response(product);
        productResponseDto.addProductDtList(product);
        productResponseDto.addProductPriceList(product);

        return productResponseDto;
    }

    @Transactional
    public Map<String, Object> findProductByKeyword(String keyword) {
        Map<String, Object> result = new HashMap<>();
        List<ProductDTO.Response> list = new ArrayList<>();
        List<Product> productList = productRepository.findByKeyword(keyword);

        for(Product product : productList) {
            ProductDTO.Response productResponseDto = new ProductDTO.Response(product);
            productResponseDto.addProductDtList(product);
            productResponseDto.addProductPriceList(product);
            list.add(productResponseDto);
        }

        result.put("RESULT", list);
        return result;
    }

    @Transactional
    public HashMap<String, Object> findProductAll() {
        HashMap<String, Object> result = new HashMap<>();
        List<ProductDTO.Response> list = new ArrayList<>();
        List<Product> productList = productRepository.findAll();

        for(Product product : productList) {
            ProductDTO.Response productResponseDto = new ProductDTO.Response(product);
            productResponseDto.addProductDtList(product);
            productResponseDto.addProductPriceList(product);
            list.add(productResponseDto);
        }

        result.put("RESULT", list);

        return result;
    }

    @Transactional
    public Long saveProductDt(List<ProductDtDTO.Request> productDTRequestDtoList, Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));
        for(ProductDtDTO.Request productDto : productDTRequestDtoList) {
            productDtRepository.save(productDto.toEntity(product));
        }

        return product.getProductNo();
    }

    // 단품 색상 변경
    @Transactional
    public Long updateProductDtColor(ProductDtDTO.Request productDTRequestDto) {
        ProductDT productDt = productDtRepository.findById(productDTRequestDto.getProductDtNo()).orElseThrow(NoSuchElementException::new);

        productDt.updateColor(productDTRequestDto.getColorCode(), productDTRequestDto.getColorName());

        return productDt.getProduct().getProductNo();
    }

    @Transactional
    public Long updateProductDtSize(ProductDtDTO.Request productDTRequestDto) {
        ProductDT productDt = productDtRepository.findById(productDTRequestDto.getProductDtNo()).orElseThrow(NoSuchElementException::new);

        productDt.updateSize(productDTRequestDto.getSizeCode(), productDTRequestDto.getSizeName());

        return productDt.getProduct().getProductNo();
    }

    public ProductDtDTO.Response findProductDtByProductDtNo(Long productDtNo) {
        ProductDT productDT = productDtRepository.findById(productDtNo).orElseThrow(() -> new NoSuchElementException("단품을 찾을 수 없습니다."));

        return new ProductDtDTO.Response(productDT);
    }


}
