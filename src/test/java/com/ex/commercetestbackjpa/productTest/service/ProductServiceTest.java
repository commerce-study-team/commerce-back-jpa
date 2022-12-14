package com.ex.commercetestbackjpa.productTest.service;

import com.ex.commercetestbackjpa.domain.dto.comment.CommentDTO;
import com.ex.commercetestbackjpa.domain.dto.comment.CommentImageDTO;
import com.ex.commercetestbackjpa.domain.dto.common.RankDTO;
import com.ex.commercetestbackjpa.domain.dto.product.*;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import com.ex.commercetestbackjpa.repository.cache.CacheRepository;
import com.ex.commercetestbackjpa.repository.product.*;
import com.ex.commercetestbackjpa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductDtRepository productDtRepository;
    @Mock
    ProductPriceRepository productPriceRepository;
    @Mock
    ProductImageRepository productImageRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    CommentImageRepository commentImageRepository;
    @Mock
    CacheRepository redisRepository;

    @Test
    public void saveProductTest() throws IOException {
        // given
        ProductDTO.Request productRequestDto = new ProductDTO.Request();
        List<ProductDtDTO.Request> productDTRequestDtoList = new ArrayList<>();
        List<ProductPriceDTO.Request> productPriceRequestDtoList = new ArrayList<>();
        List<ProductImageDTO.Request> productImageRequestDtoList = new ArrayList<>();

        // ?????? ?????????
        productRequestDto.setProductName("????????? ?????? ?????????");
        productRequestDto.setKeyword("?????????");
        productRequestDto.setLgroup("10");
        productRequestDto.setMgroup("10");
        productRequestDto.setSgroup("10");
        // ?????? ????????? END

        // ?????? ?????????
        ProductDtDTO.Request productDTRequestDto1 = new ProductDtDTO.Request();
        productDTRequestDto1.setProductDtName("???????????????");
        productDTRequestDto1.setColorCode("10");
        productDTRequestDto1.setColorName("??????");
        productDTRequestDto1.setSizeCode("10");
        productDTRequestDto1.setSizeName("???");
        productDTRequestDto1.setImage("?????????");

        ProductDtDTO.Request productDTRequestDto2 = new ProductDtDTO.Request();
        productDTRequestDto2.setProductDtName("???????????????");
        productDTRequestDto2.setColorCode("10");
        productDTRequestDto2.setColorName("??????");
        productDTRequestDto2.setSizeCode("10");
        productDTRequestDto2.setSizeName("???");
        productDTRequestDto2.setImage("?????????");

        productDTRequestDtoList.add(productDTRequestDto1);
        productDTRequestDtoList.add(productDTRequestDto2);
        // ?????? ????????? END

        // ?????? ?????????
        ProductPriceDTO.Request productPriceRequestDto1 = new ProductPriceDTO.Request();
        productPriceRequestDto1.setApplyDate(LocalDateTime.parse("2022-11-09T11:20:10"));
        productPriceRequestDto1.setSalePrice(5000L);
        productPriceRequestDto1.setCostPrice(2000L);

        ProductPriceDTO.Request productPriceRequestDto2 = new ProductPriceDTO.Request();
        productPriceRequestDto2.setApplyDate(LocalDateTime.parse("2022-12-25T12:15:30"));
        productPriceRequestDto2.setSalePrice(6000L);
        productPriceRequestDto2.setCostPrice(2000L);
        // ?????? ????????? END

        productPriceRequestDtoList.add(productPriceRequestDto1);
        productPriceRequestDtoList.add(productPriceRequestDto2);

        // ????????? ????????? -- MockObject ??????
        ProductImageDTO.Request productImageRequestDto = new ProductImageDTO.Request();
        productImageRequestDto.setImageRealName("?????????.png");

        String filePath = "src/test/resources/testImage/test.png";
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "?????????.png", "image/png",
                new FileInputStream(filePath));

        productImageRequestDto.setImgFile(multipartFile);
        productImageRequestDtoList.add(productImageRequestDto);
        // ????????? ????????? END

        productRequestDto.setProductDtRequestDtoList(productDTRequestDtoList);
        productRequestDto.setProductPriceRequestDtoList(productPriceRequestDtoList);
        productRequestDto.setProductImageRequestDtoList(productImageRequestDtoList);

        // stub
        when(productRepository.save(any(Product.class))).thenReturn(productRequestDto.toEntity());
        when(productRepository.findById(any())).thenReturn(Optional.of(productRequestDto.toEntity()));
        when(productDtRepository.save(any(ProductDT.class))).thenReturn(productDTRequestDto1.toEntity());
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(productPriceRequestDto1.toEntity());

        // when
        Long productNo = productService.saveProduct(productRequestDto);

        // then

    }

    @Test
    public void ??????????????????() {
        ProductDTO.Request productRequestDto = new ProductDTO.Request();

        productRequestDto.setProductNo(1L);
        productRequestDto.setProductName("????????? ?????? ??????");
        productRequestDto.setKeyword("?????????");
        productRequestDto.setLgroup("10");
        productRequestDto.setMgroup("10");
        productRequestDto.setSgroup("10");
        productRequestDto.setSaleFlag("00");
        productRequestDto.setSignFlag("10");

        Long productNo = productService.updateProduct(productRequestDto);

        ProductDTO.Response changeProductDTO = productService.findProductByProductNo(productNo);

        assertThat(productRequestDto.getProductName()).isEqualTo(changeProductDTO.getProductName());
    }

    @Test
    void ??????????????????() {
        ProductDTO.Response productResponseDto = productService.findProductByProductNo(1L);

        assertThat(productResponseDto.getProductNo()).isNotNull();
        assertThat(productResponseDto.getProductDtResponseDtoList().get(0).getProductDtNo()).isNotNull();
        assertThat(productResponseDto.getProductPriceResponseDto().getProductPriceNo()).isNotNull();
    }

    @Test
    void ???????????????() {
        Map<String, String> filterMap = new HashMap<>();

        filterMap.put("keyword", "??????????????????");
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "productNo");

        List<ProductDTO.Response> list = productService.findProductByFilters(filterMap, pageable);

        for(ProductDTO.Response pr : list) {
            assertThat(pr.getKeyword()).isEqualTo("?????????");
        }
    }

    @Test
    void ??????????????????() {
        Map<String, String> filterMap = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "productNo");
        List<ProductDTO.Response> list = productService.findProductByFilters(filterMap, pageable);

        assertThat(list.size()).isNotZero();
    }

    @Test
    void ????????????() {
        List<ProductDtDTO.Request> productDTRequestDtoList = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto1 = new ProductDtDTO.Request();
        productDTRequestDto1.setProductDtName("???????????????");
        productDTRequestDto1.setColorCode("10");
        productDTRequestDto1.setColorName("??????");
        productDTRequestDto1.setSizeCode("10");
        productDTRequestDto1.setSizeName("???");
        productDTRequestDto1.setImage("?????????");

        ProductDtDTO.Request productDTRequestDto2 = new ProductDtDTO.Request();
        productDTRequestDto2.setProductDtName("???????????????");
        productDTRequestDto2.setColorCode("10");
        productDTRequestDto2.setColorName("??????");
        productDTRequestDto2.setSizeCode("10");
        productDTRequestDto2.setSizeName("???");
        productDTRequestDto2.setImage("?????????");

        productDTRequestDtoList.add(productDTRequestDto1);
        productDTRequestDtoList.add(productDTRequestDto2);

        Long productNo = productService.saveProductDt(productDTRequestDtoList, 1L);

        ProductDTO.Response productResponseDto = productService.findProductByProductNo(productNo);
        List<ProductDtDTO.Response> productDtResponseDtoList = productResponseDto.getProductDtResponseDtoList();

        assertThat(productDtResponseDtoList.size()).isNotZero();

    }

    @Test
    void ??????????????????() {
        List<ProductDtDTO.Request> productDTRequestDtoList = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto1 = new ProductDtDTO.Request();
        productDTRequestDto1.setProductDtName("???????????????");

        ProductDtDTO.Request productDTRequestDto2 = new ProductDtDTO.Request();
        productDTRequestDto2.setProductDtName("???????????????");

        productDTRequestDtoList.add(productDTRequestDto1);
        productDTRequestDtoList.add(productDTRequestDto2);

        assertThrows(NoSuchElementException.class, () -> productService.saveProductDt(productDTRequestDtoList, 1000L));

    }

    @Test
    void ????????????() {
        List<ProductDtDTO.Request> list = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto = new ProductDtDTO.Request();
        productDTRequestDto.setProductDtNo(1L);
        productDTRequestDto.setColorCode("20");
        productDTRequestDto.setColorName("??????");
        productDTRequestDto.setSizeCode("20");
        productDTRequestDto.setSizeName("???");

        list.add(productDTRequestDto);

        Long productNo = productService.updateProductDt(list, 1L);

        ProductDtDTO.Response productDtResponseDto = productService.findProductDtByProductDtNo(productNo);

        assertThat(productDTRequestDto.getColorCode()).isEqualTo(productDtResponseDto.getColorCode());
        assertThat(productDTRequestDto.getSizeCode()).isEqualTo(productDtResponseDto.getSizeCode());
    }

    @Test
    void ??????????????????() {
        List<ProductDtDTO.Request> list = new ArrayList<>();
        ProductDtDTO.Request productDTRequestDto = new ProductDtDTO.Request();
        productDTRequestDto.setProductDtNo(0L);
        productDTRequestDto.setColorCode("20");
        productDTRequestDto.setColorName("??????");
        productDTRequestDto.setSizeCode("20");
        productDTRequestDto.setSizeName("???");

        list.add(productDTRequestDto);

        assertThrows(NoSuchElementException.class, () -> productService.updateProductDt(list, 1L));
    }

    @Test
    void ??????????????????????????????() {
        List<ProductPriceDTO.Request> list = new ArrayList<>();
        ProductPriceDTO.Request productPriceRequestDTO = new ProductPriceDTO.Request();
        productPriceRequestDTO.setProductPriceNo(1L);
        productPriceRequestDTO.setSalePrice(10000L);
        productPriceRequestDTO.setCostPrice(2000L);
        productPriceRequestDTO.setUseYn(true);
        list.add(productPriceRequestDTO);

        Long productNo = productService.updateProductPrice(list, 1L);

        ProductDTO.Response productDTO= productService.findProductByProductNo(productNo);

        assertThat(productDTO.getProductPriceResponseDto().getUseYn()).isEqualTo(true);
    }

    @Test
    void ????????????() {
        List<ProductPriceDTO.Request> productPriceRequestDtoList = new ArrayList<>();
        // ?????? ?????????
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
        // ?????? ????????? END

        productPriceRequestDtoList.add(productPriceRequestDto1);
        productPriceRequestDtoList.add(productPriceRequestDto2);

        Long productNo = productService.saveProductPrice(productPriceRequestDtoList, 1L);

        ProductDTO.Response productResponseDto = productService.findProductByProductNo(productNo);
        ProductPriceDTO.Response productPriceResponseDto = productResponseDto.getProductPriceResponseDto();

        assertThat(productPriceResponseDto).isNotNull();
    }

    @Test
    void ?????????????????????() throws Exception {
        List<ProductImageDTO.Request> productImageRequestDtoList = new ArrayList<>();
        // ????????? ????????? -- MockObject ??????
        ProductImageDTO.Request productImageRequestDto = new ProductImageDTO.Request();
        productImageRequestDto.setImageRealName("?????????.png");

        String filePath = "src/test/resources/testImage/test.png";
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "?????????.png", "image/png",
                new FileInputStream(filePath));

        productImageRequestDto.setImgFile(multipartFile);
        productImageRequestDtoList.add(productImageRequestDto);
        // ????????? ????????? END

        Long productNo = productService.saveProductImage(productImageRequestDtoList, 2L);

        assertThat(productService.findProductByProductNo(productNo).getProductImageResponseDtoList().size()).isNotZero();
    }

    @Test
    void ?????????????????????() {
        Long productNo = productService.deleteProductImage(2L);
    }

    @Test
    void ???????????????() throws Exception {
        CommentDTO.Request commentRequestDto = new CommentDTO.Request();
        List<CommentImageDTO.Request> commentImageRequestDtos = new ArrayList<>();
        commentRequestDto.setTitle("????????? ????????????!");
        commentRequestDto.setContent("?????? ?????? ?????? ?????????");
        commentRequestDto.setGrade(5);
        commentRequestDto.setCustNo(1L);
        commentRequestDto.setProductNo(1L);
        commentRequestDto.setOrderNo(1L);

        // ????????? ????????? -- MockObject ??????
        CommentImageDTO.Request commentImageRequestDto = new CommentImageDTO.Request();
        commentImageRequestDto.setImageRealName("?????????.png");

        String filePath = "src/test/resources/testImage/test.png";
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "?????????.png", "image/png",
                new FileInputStream(filePath));

        commentImageRequestDto.setImgFile(multipartFile);
        commentImageRequestDtos.add(commentImageRequestDto);
        // ????????? ????????? END

        commentRequestDto.setCommentImageRequestDtoList(commentImageRequestDtos);

        Long productNo = productService.saveComment(commentRequestDto);

        ProductDTO.Response productResponseDTO = productService.findProductByProductNo(1L);
        assertThat(productResponseDTO.getProductCommentResponseDtoList().size()).isNotZero();
    }

    @Test
    void ?????????????????????() {
        List<RankDTO.Response> responses = productService.searchRankList(LocalDate.now());

        for(RankDTO.Response response : responses) {
            System.out.println("??????????????? : " + response.getItemId());
            System.out.println("??????????????? score : " + response.getScore());
        }
    }

    @Test
    void ???????????????() throws Exception {
        CommentDTO.Request commentRequestDto = new CommentDTO.Request();
        List<CommentImageDTO.Request> commentImageRequestDtos = new ArrayList<>();
        commentRequestDto.setCommnetNo(3L);
        commentRequestDto.setTitle("????????? ?????? ????????????!");
        commentRequestDto.setContent("?????? ?????? ?????? ?????????. ???????????????");
        commentRequestDto.setGrade(5);
        commentRequestDto.setCustNo(1L);
        commentRequestDto.setProductNo(1L);
        commentRequestDto.setOrderNo(1L);

        // ????????? ????????? -- MockObject ??????
        CommentImageDTO.Request commentImageRequestDto = new CommentImageDTO.Request();
        commentImageRequestDto.setImageRealName("???????????????.png");

        String filePath = "src/test/resources/testImage/test.png";
        MockMultipartFile multipartFile = new MockMultipartFile("image",
                "?????????.png", "image/png",
                new FileInputStream(filePath));

        commentImageRequestDto.setImgFile(multipartFile);
        commentImageRequestDto.setRemove(false);
        commentImageRequestDtos.add(commentImageRequestDto);
        // ????????? ????????? END

        commentRequestDto.setCommentImageRequestDtoList(commentImageRequestDtos);

        Long productNo = productService.updateComment(commentRequestDto);

        ProductDTO.Response productResponseDTO = productService.findProductByProductNo(1L);
        assertThat(productResponseDTO.getProductCommentResponseDtoList().size()).isNotZero();

    }

    @Test
    void ???????????????() {
        productService.deleteComment(5L);
    }
}
