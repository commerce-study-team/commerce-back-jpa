package com.ex.commercetestbackjpa.service.product;

import com.ex.commercetestbackjpa.config.util.FileUtil;
import com.ex.commercetestbackjpa.domain.dto.comment.CommentDTO;
import com.ex.commercetestbackjpa.domain.dto.product.*;
import com.ex.commercetestbackjpa.domain.entity.comment.Comment;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductImage;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import com.ex.commercetestbackjpa.repository.comment.CommentRepository;
import com.ex.commercetestbackjpa.repository.product.ProductDtRepository;
import com.ex.commercetestbackjpa.repository.product.ProductImageRepository;
import com.ex.commercetestbackjpa.repository.product.ProductPriceRepository;
import com.ex.commercetestbackjpa.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtRepository productDtRepository;

    private final ProductPriceRepository productPriceRepository;

    private final ProductImageRepository productImageRepository;

    private final CommentRepository commentRepository;

    /**
     * 상품 저장
     * @param productRequestDto
     * @return Long
     */
    @Transactional
    public Long saveProduct(ProductDTO.Request productRequestDto) {
        Product product = productRequestDto.toEntity();
        List<ProductDtDTO.Request> productDTRequestDtoList = productRequestDto.getProductDtRequestDtoList();
        List<ProductPriceDTO.Request> productPriceRequestDtoList = productRequestDto.getProductPriceRequestDtoList();
        List<ProductImageDTO.Request> productImageRequestDtoList = productRequestDto.getProductImageRequestDtoList();

        Long productNo = productRepository.save(product).getProductNo();

        // 단품 저장
        this.saveProductDt(productDTRequestDtoList, productNo);

        // 가격 저장
        this.saveProductPrice(productPriceRequestDtoList, productNo);

        // 이미지는 필수가 아니기 때문에 데이터 있을 때 저장
        if(productImageRequestDtoList != null) {
            this.saveProductImage(productImageRequestDtoList, productNo);
        }

        return productNo;
    }

    /**
     * 상품 List 조회
     * @param filterMap
     * @param pageable
     * @return Map<String, Object>
     */
    @Transactional(readOnly = true)
    public List<ProductDTO.Response> findProductByFilters(Map<String, String> filterMap, Pageable pageable) {
        List<ProductDTO.Response> result = new ArrayList<>();
        Page<Product> productList = productRepository.findByFilters(filterMap, pageable);

        for(Product product : productList.getContent()) {
            ProductDTO.Response productResponseDto = new ProductDTO.Response(product);

            // 다건 상품조회 시 단품 정보 불필요
            productResponseDto.productInfoSettings(null, product.getProductPriceList(), product.getProductImageList());

            result.add(productResponseDto);
        }

        return result;
    }

    public ProductDTO.Response findProductByProductNo(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
        ProductDTO.Response productResponseDto = new ProductDTO.Response(product);
        productResponseDto.productInfoSettings(product.getProductDtList(), product.getProductPriceList(), product.getProductImageList());

        return productResponseDto;
    }

    /**
     * 상품 변경
     * @param productRequestDto
     * @return Long
     */
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

    /**
     * 단품 저장
     * @param productDTRequestDtoList
     * @param productNo
     * @return Long
     */
    @Transactional
    public Long saveProductDt(List<ProductDtDTO.Request> productDTRequestDtoList, Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));

        for(ProductDtDTO.Request productDtDto : productDTRequestDtoList) {
            ProductDT productDt = productDtDto.toEntity();
            productDt.settingProduct(product);
            System.out.println(product.getProductDtList().get(0));
            productDtRepository.save(productDt);
        }

        return productNo;
    }

    /**
     * 단품 변경
     * @param productDTRequestDtoList
     * @param productNo
     * @return Long
     */
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

    /**
     * 단품 조회
     * @param productDtNo
     * @return ProductDtDTO.Response
     */
    public ProductDtDTO.Response findProductDtByProductDtNo(Long productDtNo) {
        ProductDT productDt = productDtRepository.findById(productDtNo).orElseThrow(() -> new NoSuchElementException("단품 정보를 찾을 수 없습니다."));
        return new ProductDtDTO.Response(productDt);
    }

    /**
     * 가격 저장
     * @param productPriceRequestDtoList
     * @param productNo
     * @return Long
     */
    @Transactional
    public Long saveProductPrice(List<ProductPriceDTO.Request> productPriceRequestDtoList, Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
        for(ProductPriceDTO.Request productPriceDto : productPriceRequestDtoList) {
            ProductPrice productPrice = productPriceDto.toEntity();
            productPrice.settingProduct(product);
            productPriceRepository.save(productPrice);
        }

        return product.getProductNo();
    }


    /**
     * 가격 변경
     * @param productPriceRequestDtoList
     * @param productNo
     * @return
     */
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

    /**
     * 상품 이미지 저장
     * @param productImageRequestDtoList
     * @param productNo
     * @return Long
     */
    @Transactional
    public Long saveProductImage(List<ProductImageDTO.Request> productImageRequestDtoList, Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));

        for(ProductImageDTO.Request productImageRequestDto : productImageRequestDtoList) {
            String imageName = FileUtil.uploadFile(productImageRequestDto.getImgFile());
            ProductImage productImage = productImageRequestDto.toEntity();
            productImage.settingProduct(product);
            productImage.settingImageName(imageName);
            productImageRepository.save(productImage);
        }

        return productNo;
    }

    /**
     * 상품 이미지 삭제
     * @param productImageNo
     * @return Long
     */
    @Transactional
    public Long deleteProductImage(Long productImageNo) {
        ProductImage productImage = productImageRepository.findById(productImageNo).orElseThrow(() -> new NoSuchElementException("상품 이미지를 찾을 수 없습니다."));

        if(FileUtil.deleteFile(productImage.getImageName())) {
            productImageRepository.deleteById(productImageNo);
        }

        return productImage.getProduct().getProductNo();
    }

    @Transactional
    public Long saveComment(CommentDTO.Request commentRequestDto) {
        Product product = productRepository.findById(commentRequestDto.getProductNo()).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));

        // 회원 데이터 체크 로직 추가 예정
        // Customer customer = customerRepository.findById(commentRequestDto.getCustNo()).orElseThrow(() -> new NoSuchElementException("회원 정보를 찾을 수 없습니다."));

        // 주문 데이터 체크 로직 추가 예정
        // 고객이 해당 주문 건으로 작성한 리뷰가 있는지 확인
        // Order order = orderRepository.findById(commentRequestDto.getOrderNo()).orElseThrow(() -> new NoSuchElementException("주문 정보를 찾을 수 없습니다."));

        // 비회원 주문일 경우 분기 처리 예정?? 비회원 주문 가능하게 변경 될 경우 수정 필요

        Comment comment = commentRequestDto.toEntity();

        comment.settingProduct(product);
        comment.settingCustomer(commentRequestDto.getCustNo());
        comment.settingOrder(commentRequestDto.getOrderNo());

        commentRepository.save(comment);

        Long count = commentRepository.countByProduct(product); // 싱크를 맞추기 위해서 재검색.. 효율성 판단하여 제거 예정

        product.updateCommentCount(count+1); // 집계로 처리?? Transaction 내에서 영속성 컨텍스트 값을 조회하여 + 처리하는게 맞는지 검토

        return product.getProductNo();
    }
}
