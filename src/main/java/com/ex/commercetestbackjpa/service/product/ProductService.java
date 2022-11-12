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
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
        ProductDTO.Response productResponseDto = new ProductDTO.Response(product);
        productResponseDto.addProductDtList(product);
        productResponseDto.findProductPrice(product);

        return productResponseDto;
    }

    @Transactional
    public Map<String, Object> findProductByOptions(String option, String filterValue) {
        Map<String, Object> result = new HashMap<>();
        List<ProductDTO.Response> list = new ArrayList<>();
        List<Product> productList = null;

        switch (option) {
            case "keyword" :
                productList = productRepository.findByKeyword(filterValue);
                break;
            case "productName" :
                productList = productRepository.findByProductName(filterValue);
                break;
            case "saleFlag" :
                productList = productRepository.findBySaleFlag(filterValue);
                break;
            case "signFlag" :
                productList = productRepository.findBySignFlag(filterValue);
                break;
            default:
                productList = productRepository.findAll();
        }

        for(Product product : productList) {
            ProductDTO.Response productResponseDto = new ProductDTO.Response(product);
            productResponseDto.addProductDtList(product);
            productResponseDto.findProductPrice(product);
            list.add(productResponseDto);
        }

        result.put("RESULT", list);
        return result;
    }

    @Transactional
    public Long updateProduct(ProductDTO.Request productRequestDto) {
        Product product = productRepository.findById(productRequestDto.getProductNo()).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));

        product.updateProductName(productRequestDto.getProductName());
        product.updateKeyword(productRequestDto.getKeyword());
        product.updateSaleFlag(productRequestDto.getSaleFlag());
        product.updateLMSgroup(productRequestDto.getLgroup(), productRequestDto.getMgroup(), productRequestDto.getSgroup());
        product.updateMaxBuy(product.getMaxBuy());
        product.updateSignFlag(product.getSignFlag());

        return product.getProductNo();
    }

    @Transactional
    public Long saveProductDt(List<ProductDtDTO.Request> productDTRequestDtoList, Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
        for(ProductDtDTO.Request productDtDto : productDTRequestDtoList) {
            productDtRepository.save(productDtDto.toEntity(product));
        }

        return productNo;
    }

    @Transactional
    public Long updateProductDt(List<ProductDtDTO.Request> productDTRequestDtoList, Long productNo) {
        for(ProductDtDTO.Request productDTRequestDto : productDTRequestDtoList) {
            ProductDT productDt = productDtRepository.findById(productDTRequestDto.getProductDtNo()).orElseThrow(() -> new NoSuchElementException("단품 정보를 찾을 수 없습니다."));
            productDt.updateColor(productDTRequestDto.getColorCode(), productDTRequestDto.getColorName());
            productDt.updateSize(productDTRequestDto.getSizeCode(), productDTRequestDto.getSizeName());
            productDt.updateSaleFlag(productDTRequestDto.getSaleFlag());
        }

        return productNo;
    }

    public ProductDtDTO.Response findProductDtByProductDtNo(Long productDtNo) {
        ProductDT productDt = productDtRepository.findById(productDtNo).orElseThrow(() -> new NoSuchElementException("단품 정보를 찾을 수 없습니다."));
        return new ProductDtDTO.Response(productDt);
    }

    @Transactional
    public Long saveProductPrice(List<ProductPriceDTO.Request> productPriceRequestDtoList, Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
        for(ProductPriceDTO.Request productPriceDto : productPriceRequestDtoList) {
            productPriceRepository.save(productPriceDto.toEntity(product));
        }

        return product.getProductNo();
    }

    @Transactional
    public Long updateProductPrice(List<ProductPriceDTO.Request> productPriceRequestDtoList, Long productNo) {

        for(ProductPriceDTO.Request productPriceDto : productPriceRequestDtoList) {
            ProductPrice productPrice = productPriceRepository.findById(productPriceDto.getProductPriceNo()).orElseThrow(() -> new NoSuchElementException("가격 정보를 찾을 수 없습니다."));
            productPrice.updateSalePrice(productPriceDto.getSalePrice());
            productPrice.updateCostPrice(productPriceDto.getCostPrice());
            productPrice.updateUseYn(productPriceDto.getUseYn());
        }

        return productNo;
    }
}
