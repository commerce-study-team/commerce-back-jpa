package com.ex.commercetestbackjpa.productTest;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTRequestDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductRequestDto;
import com.ex.commercetestbackjpa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService ProductService;

    @Test
    public void saveProductTest() {
        ProductRequestDto productRequestDto = new ProductRequestDto();

        productRequestDto.setProductName("테스트 상품");
        productRequestDto.setKeyword("테스트");
        productRequestDto.setLgroup("10");
        productRequestDto.setMgroup("10");
        productRequestDto.setSgroup("10");

        List<ProductDTRequestDto> productDTList = new ArrayList<>();

        ProductDTRequestDto productDTRequestDto = new ProductDTRequestDto();
        productDTRequestDto.setProductNameDt("빨강붕어빵");
        productDTRequestDto.setColorCode("10");
        productDTRequestDto.setColorName("빨강");
        productDTRequestDto.setSizeCode("10");
        productDTRequestDto.setSizeName("중");
        productDTRequestDto.setImage("이미지");

        productDTList.add(productDTRequestDto);

        ProductDTRequestDto productDTRequestDto2 = new ProductDTRequestDto();
        productDTRequestDto2.setProductNameDt("노랑붕어빵");
        productDTRequestDto2.setColorCode("10");
        productDTRequestDto2.setColorName("노랑");
        productDTRequestDto2.setSizeCode("10");
        productDTRequestDto2.setSizeName("대");
        productDTRequestDto2.setImage("이미지");

        productDTList.add(productDTRequestDto2);

        productRequestDto.setProductDtRequestDtos(productDTList);

        ProductService.saveProduct(productRequestDto);
    }

}
