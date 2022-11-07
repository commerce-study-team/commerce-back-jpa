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
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("조회된 상품이 없습니다."));
        ProductResponseDto productResponseDto = new ProductResponseDto(product);
        productResponseDto.setProductDtResponseDtoList(this.addProductDtList(product));
        productResponseDto.setProductPriceResponseDtoList(this.addProductPriceList(product));

        return productResponseDto;
    }

    @Transactional
    public Map<String, Object> findProductByProductName(String productName) {
        Map<String, Object> result = new HashMap<>();
        List<ProductResponseDto> list = new ArrayList<>();
        List<Product> productList = productRepository.findByProductName(productName);

        for(Product product : productList) {
            ProductResponseDto productResponseDto = new ProductResponseDto(product);
            productResponseDto.setProductDtResponseDtoList(this.addProductDtList(product));
            productResponseDto.setProductPriceResponseDtoList(this.addProductPriceList(product));
            list.add(productResponseDto);
        }

        result.put("RESULT", list);
        return result;
    }

    @Transactional
    public HashMap<String, Object> findProductAll() {
        HashMap<String, Object> result = new HashMap<>();
        List<ProductResponseDto> list = new ArrayList<>();
        List<Product> productList = productRepository.findAll();

        for(Product product : productList) {
            ProductResponseDto productResponseDto = new ProductResponseDto(product);
            productResponseDto.setProductDtResponseDtoList(this.addProductDtList(product));
            productResponseDto.setProductPriceResponseDtoList(this.addProductPriceList(product));
            list.add(productResponseDto);
        }

        result.put("RESULT", list);

        return result;
    }

    private List<ProductDtResponseDto> addProductDtList(Product product) {
        List<ProductDtResponseDto> productDtResponseDtoList = new ArrayList<>();
        List<ProductDT> productDTList = product.getProductDtList();

        // 단품 add
        for (ProductDT productDT : productDTList) {
            productDtResponseDtoList.add(new ProductDtResponseDto(productDT));
        }

        return productDtResponseDtoList;
    }

    private List<ProductPriceResponseDto> addProductPriceList(Product product) {
        List<ProductPriceResponseDto> productPriceResponseDtoList = new ArrayList<>();
        List<ProductPrice> productPriceList = product.getProductPriceList();

        // 가격 add
        for (ProductPrice productPrice : productPriceList) {
            productPriceResponseDtoList.add(new ProductPriceResponseDto(productPrice));
        }

        return productPriceResponseDtoList;
    }

    @Transactional
    public Long saveProductDt(ProductDTRequestDto productDTRequestDto) {
        Product product = productRepository.findById(productDTRequestDto.getProductNo()).get();
        ProductDT productDT = productDTRequestDto.toEntity(product);

        return productDtRepository.save(productDT).getProductDtNo();
    }

    // 단품 색상 변경
    @Transactional
    public Long updateProductDtColor(ProductDTRequestDto productDTRequestDto) {
        ProductDT productDt = productDtRepository.findById(productDTRequestDto.getProductDtNo()).orElseThrow(NoSuchElementException::new);

        productDt.updateColor(productDTRequestDto.getColorCode(), productDTRequestDto.getColorName());

        return productDt.getProduct().getProductNo();
    }

    @Transactional
    public Long updateProductDtSize(ProductDTRequestDto productDTRequestDto) {
        ProductDT productDt = productDtRepository.findById(productDTRequestDto.getProductDtNo()).orElseThrow(NoSuchElementException::new);

        productDt.updateSize(productDTRequestDto.getSizeCode(), productDTRequestDto.getSizeName());

        return productDt.getProduct().getProductNo();
    }

    public ProductDtResponseDto findProductDtByProductDtNo(Long productDtNo) {
        ProductDT productDT = productDtRepository.findById(productDtNo).get();

        return new ProductDtResponseDto(productDT);
    }


}
