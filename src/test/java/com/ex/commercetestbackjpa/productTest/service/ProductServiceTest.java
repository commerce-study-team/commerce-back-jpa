package com.ex.commercetestbackjpa.productTest.service;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTRequestDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductPriceRequestDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductRequestDto;
import com.ex.commercetestbackjpa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService ProductService;

    @Test
    public void saveProductTest() {
        ProductRequestDto productRequestDto = new ProductRequestDto();
        List<ProductDTRequestDto> productDTRequestDtoList = new ArrayList<>();
        List<ProductPriceRequestDto> productPriceRequestDtoList = new ArrayList<>();

        // 상품 데이터
        productRequestDto.setProductName("테스트 상품");
        productRequestDto.setKeyword("테스트");
        productRequestDto.setLgroup("10");
        productRequestDto.setMgroup("10");
        productRequestDto.setSgroup("10");
        // 상품 데이터 END

        // 단품 데이터
        ProductDTRequestDto productDTRequestDto1 = new ProductDTRequestDto();
        productDTRequestDto1.setProductDtName("빨강붕어빵");
        productDTRequestDto1.setColorCode("10");
        productDTRequestDto1.setColorName("빨강");
        productDTRequestDto1.setSizeCode("10");
        productDTRequestDto1.setSizeName("중");
        productDTRequestDto1.setImage("이미지");

        ProductDTRequestDto productDTRequestDto2 = new ProductDTRequestDto();
        productDTRequestDto2.setProductDtName("노랑붕어빵");
        productDTRequestDto2.setColorCode("10");
        productDTRequestDto2.setColorName("노랑");
        productDTRequestDto2.setSizeCode("10");
        productDTRequestDto2.setSizeName("대");
        productDTRequestDto2.setImage("이미지");

        productDTRequestDtoList.add(productDTRequestDto1);
        productDTRequestDtoList.add(productDTRequestDto2);
        // 단품 데이터 END

        // 가격 데이터
        ProductPriceRequestDto productPriceRequestDto1 = new ProductPriceRequestDto();
        productPriceRequestDto1.setApplyDate(LocalDateTime.parse("2022-11-12T11:20:10"));
        productPriceRequestDto1.setSalePrice(5000L);
        productPriceRequestDto1.setCostPrice(2000L);
        productPriceRequestDto1.setMargin(productPriceRequestDto1.getSalePrice() - productPriceRequestDto1.getCostPrice());

        ProductPriceRequestDto productPriceRequestDto2 = new ProductPriceRequestDto();
        productPriceRequestDto2.setApplyDate(LocalDateTime.parse("2022-12-25T12:15:30"));
        productPriceRequestDto2.setSalePrice(6000L);
        productPriceRequestDto2.setCostPrice(2000L);
        productPriceRequestDto2.setMargin(productPriceRequestDto2.getSalePrice() - productPriceRequestDto2.getCostPrice());
        // 가격 데이터 END

        productPriceRequestDtoList.add(productPriceRequestDto1);
        productPriceRequestDtoList.add(productPriceRequestDto2);

        productRequestDto.setProductDtRequestDtos(productDTRequestDtoList);
        productRequestDto.setProductPriceRequestDtos(productPriceRequestDtoList);

        ProductService.saveProduct(productRequestDto);
    }

}
