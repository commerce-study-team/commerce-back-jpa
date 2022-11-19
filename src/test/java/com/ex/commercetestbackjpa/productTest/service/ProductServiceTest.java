package com.ex.commercetestbackjpa.productTest.service;

import com.ex.commercetestbackjpa.domain.dto.comment.CommentDTO;
import com.ex.commercetestbackjpa.domain.dto.product.*;
import com.ex.commercetestbackjpa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    public void 상품저장() throws IOException {
        ProductDTO.Request productRequestDto = new ProductDTO.Request();
        List<ProductDtDTO.Request> productDTRequestDtoList = new ArrayList<>();
        List<ProductPriceDTO.Request> productPriceRequestDtoList = new ArrayList<>();
        List<ProductImageDTO.Request> productImageRequestDtoList = new ArrayList<>();

        // 상품 데이터
        productRequestDto.setProductName("테스트 상품");
        productRequestDto.setKeyword("테스트");
        productRequestDto.setLgroup("10");
        productRequestDto.setMgroup("10");
        productRequestDto.setSgroup("10");
        // 상품 데이터 END

        // 단품 데이터
        ProductDtDTO.Request productDTRequestDto1 = new ProductDtDTO.Request();
        productDTRequestDto1.setProductDtName("빨강붕어빵");
        productDTRequestDto1.setColorCode("10");
        productDTRequestDto1.setColorName("빨강");
        productDTRequestDto1.setSizeCode("10");
        productDTRequestDto1.setSizeName("중");
        productDTRequestDto1.setImage("이미지");

        ProductDtDTO.Request productDTRequestDto2 = new ProductDtDTO.Request();
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
        ProductPriceDTO.Request productPriceRequestDto1 = new ProductPriceDTO.Request();
        productPriceRequestDto1.setApplyDate(LocalDateTime.parse("2022-11-09T11:20:10"));
        productPriceRequestDto1.setSalePrice(5000L);
        productPriceRequestDto1.setCostPrice(2000L);

        ProductPriceDTO.Request productPriceRequestDto2 = new ProductPriceDTO.Request();
        productPriceRequestDto2.setApplyDate(LocalDateTime.parse("2022-12-25T12:15:30"));
        productPriceRequestDto2.setSalePrice(6000L);
        productPriceRequestDto2.setCostPrice(2000L);
        // 가격 데이터 END

        productPriceRequestDtoList.add(productPriceRequestDto1);
        productPriceRequestDtoList.add(productPriceRequestDto2);

        // 이미지 데이터 -- MockObject 생성
        ProductImageDTO.Request productImageRequestDto = new ProductImageDTO.Request();
        productImageRequestDto.setImageRealName("테스트.png");

        String filePath = "src/test/resources/testImage/test.png";
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "테스트.png", "image/png",
                new FileInputStream(filePath));


        UUID uuid = UUID.randomUUID();
        productImageRequestDto.setImageName(uuid + "테스트.png");
        productImageRequestDto.setImgFile(multipartFile);

        productImageRequestDtoList.add(productImageRequestDto);
        // 이미지 데이터 END

        productRequestDto.setProductDtRequestDtoList(productDTRequestDtoList);
        productRequestDto.setProductPriceRequestDtoList(productPriceRequestDtoList);
        productRequestDto.setProductImageRequestDtoList(productImageRequestDtoList);

        productService.saveProduct(productRequestDto);
    }

    @Test
    public void 상품정보변경() {
        ProductDTO.Request productRequestDto = new ProductDTO.Request();
        Map<String, String> map = new HashMap<>();

        productRequestDto.setProductNo(1L);
        productRequestDto.setProductName("테스트 상품 변경");
        productRequestDto.setKeyword("테스트");
        productRequestDto.setLgroup("10");
        productRequestDto.setMgroup("10");
        productRequestDto.setSgroup("10");
        productRequestDto.setSaleFlag("00");
        productRequestDto.setSignFlag("10");

        Long productNo = productService.updateProduct(productRequestDto);

        map.put("productNo", productNo.toString());

        ProductDTO.Response changeProductDTO = this.findProductByFilterProxy(map).get(0);

        assertThat(productRequestDto.getProductName()).isEqualTo(changeProductDTO.getProductName());
    }

    @Test
    void 단일상품검색() {
        Map<String, String> map = new HashMap<>();
        map.put("productNo", "1");
        ProductDTO.Response productResponseDto = this.findProductByFilterProxy(map).get(0);
        List<ProductDtDTO.Response> productDtResponseDtoList = productResponseDto.getProductDtResponseDtoList();
        ProductPriceDTO.Response productPriceResponseDto = productResponseDto.getProductPriceResponseDto();

        assertThat(productResponseDto.getProductNo()).isNotNull();
        assertThat(productDtResponseDtoList.get(0).getProductDtNo()).isNotNull();
        assertThat(productPriceResponseDto.getProductPriceNo()).isNotNull();
    }

    @Test
    void 키워드검색() {
        Map<String, String> filterMap = new HashMap<>();

        filterMap.put("keyword", "테스트");
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "productNo");

        List<ProductDTO.Response> list = productService.findProductByFilters(filterMap, pageable);

        for(ProductDTO.Response pr : list) {
            assertThat(pr.getKeyword()).isEqualTo("테스트");
            assertThat(pr.getProductPriceResponseDto().getProductPriceNo()).isNotNull();
            System.out.println(pr.getProductNo());
        }
    }

    @Test
    void 전체상품검색() {
        Map<String, String> filterMap = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "productNo");
        List<ProductDTO.Response> list = productService.findProductByFilters(filterMap, pageable);

        assertThat(list.size()).isNotZero();
    }

    @Test
    void 단품저장() {
        Map<String, String> map = new HashMap<>();
        map.put("productNo", "1");
        List<ProductDtDTO.Request> productDTRequestDtoList = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto1 = new ProductDtDTO.Request();
        productDTRequestDto1.setProductDtName("연두붕어빵");
        productDTRequestDto1.setColorCode("10");
        productDTRequestDto1.setColorName("연두");
        productDTRequestDto1.setSizeCode("10");
        productDTRequestDto1.setSizeName("중");
        productDTRequestDto1.setImage("이미지");

        ProductDtDTO.Request productDTRequestDto2 = new ProductDtDTO.Request();
        productDTRequestDto2.setProductDtName("파랑붕어빵");
        productDTRequestDto2.setColorCode("10");
        productDTRequestDto2.setColorName("파랑");
        productDTRequestDto2.setSizeCode("10");
        productDTRequestDto2.setSizeName("중");
        productDTRequestDto2.setImage("이미지");

        productDTRequestDtoList.add(productDTRequestDto1);
        productDTRequestDtoList.add(productDTRequestDto2);

        Long productNo = productService.saveProductDt(productDTRequestDtoList, 1L);

        ProductDTO.Response productResponseDto = this.findProductByFilterProxy(map).get(0);
        List<ProductDtDTO.Response> productDtResponseDtoList = productResponseDto.getProductDtResponseDtoList();

        assertThat(productDtResponseDtoList.size()).isNotZero();

    }

    @Test
    void 단품저장실패() {
        List<ProductDtDTO.Request> productDTRequestDtoList = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto1 = new ProductDtDTO.Request();
        productDTRequestDto1.setProductDtName("초록붕어빵");

        ProductDtDTO.Request productDTRequestDto2 = new ProductDtDTO.Request();
        productDTRequestDto2.setProductDtName("파랑붕어빵");

        productDTRequestDtoList.add(productDTRequestDto1);
        productDTRequestDtoList.add(productDTRequestDto2);

        assertThrows(NoSuchElementException.class, () -> productService.saveProductDt(productDTRequestDtoList, 1000L));

    }

    @Test
    void 단품변경() {
        List<ProductDtDTO.Request> list = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto = new ProductDtDTO.Request();
        productDTRequestDto.setProductDtNo(1L);
        productDTRequestDto.setColorCode("20");
        productDTRequestDto.setColorName("초록");
        productDTRequestDto.setSizeCode("20");
        productDTRequestDto.setSizeName("소");

        list.add(productDTRequestDto);

        productService.updateProductDt(list, 1L);

        ProductDtDTO.Response productDtResponseDto = productService.findProductDtByProductDtNo(1L);

        assertThat(productDTRequestDto.getColorCode()).isEqualTo(productDtResponseDto.getColorCode());
        assertThat(productDTRequestDto.getSizeCode()).isEqualTo(productDtResponseDto.getSizeCode());
    }

    @Test
    void 단품변경실패() {
        List<ProductDtDTO.Request> list = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto = new ProductDtDTO.Request();
        productDTRequestDto.setProductDtNo(0L);
        productDTRequestDto.setColorCode("20");
        productDTRequestDto.setColorName("초록");
        productDTRequestDto.setSizeCode("20");
        productDTRequestDto.setSizeName("소");

        list.add(productDTRequestDto);

        assertThrows(NoSuchElementException.class, () -> productService.updateProductDt(list, 1L));
    }

    @Test
    void 상품가격사용여부변경() {
        Map<String, String> map = new HashMap<>();
        map.put("productNo", "1");
        List<ProductPriceDTO.Request> list = new ArrayList<>();
        ProductPriceDTO.Request productPriceRequestDTO = new ProductPriceDTO.Request();
        productPriceRequestDTO.setProductPriceNo(1L);
        productPriceRequestDTO.setSalePrice(10000L);
        productPriceRequestDTO.setCostPrice(2000L);
        productPriceRequestDTO.setUseYn(true);
        list.add(productPriceRequestDTO);

        productService.updateProductPrice(list, 1L);

        ProductDTO.Response productDTO= this.findProductByFilterProxy(map).get(0);

        assertThat(productDTO.getProductPriceResponseDto().getUseYn()).isEqualTo(true);
    }

    @Test
    void 가격저장() {
        Map<String, String> map = new HashMap<>();
        map.put("productNo", "1");
        List<ProductPriceDTO.Request> productPriceRequestDtoList = new ArrayList<>();
        // 가격 데이터
        ProductPriceDTO.Request productPriceRequestDto1 = new ProductPriceDTO.Request();
        productPriceRequestDto1.setApplyDate(LocalDateTime.parse("2022-11-07T11:20:10"));
        productPriceRequestDto1.setSalePrice(5000L);
        productPriceRequestDto1.setCostPrice(2000L);
        productPriceRequestDto1.setMargin(productPriceRequestDto1.getSalePrice() - productPriceRequestDto1.getCostPrice());

        ProductPriceDTO.Request productPriceRequestDto2 = new ProductPriceDTO.Request();
        productPriceRequestDto2.setApplyDate(LocalDateTime.parse("2022-12-26T12:15:30"));
        productPriceRequestDto2.setSalePrice(6000L);
        productPriceRequestDto2.setCostPrice(2000L);
        productPriceRequestDto2.setMargin(productPriceRequestDto2.getSalePrice() - productPriceRequestDto2.getCostPrice());
        // 가격 데이터 END

        productPriceRequestDtoList.add(productPriceRequestDto1);
        productPriceRequestDtoList.add(productPriceRequestDto2);

        Long productNo = productService.saveProductPrice(productPriceRequestDtoList, 1L);

        ProductDTO.Response productResponseDto = this.findProductByFilterProxy(map).get(0);
        List<ProductDtDTO.Response> productDtResponseDtoList = productResponseDto.getProductDtResponseDtoList();

        assertThat(productDtResponseDtoList.size()).isNotZero();
    }

    private List<ProductDTO.Response> findProductByFilterProxy(Map<String, String> map) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "productNo");

        return productService.findProductByFilters(map, pageable);
    }

    @Test
    void 상품이미지제거() {
        Long productNo = productService.deleteProductImage(2L);
    }

    @Test
    void 상품평저장() {
        CommentDTO.Request commentRequestDto = new CommentDTO.Request();
        commentRequestDto.setTitle("상품평 남깁니다!");
        commentRequestDto.setContent("상품 품질 정말 좋네요");
        commentRequestDto.setGrade(5);
        commentRequestDto.setCustNo(1L);
        commentRequestDto.setProductNo(1L);
        commentRequestDto.setOrderNo(1L);

        Long productNo = productService.saveComment(commentRequestDto);
    }
}
