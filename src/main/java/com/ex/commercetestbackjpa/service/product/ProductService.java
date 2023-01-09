package com.ex.commercetestbackjpa.service.product;

import com.ex.commercetestbackjpa.config.util.FileUtil;
import com.ex.commercetestbackjpa.domain.dto.comment.CommentDTO;
import com.ex.commercetestbackjpa.domain.dto.comment.CommentImageDTO;
import com.ex.commercetestbackjpa.domain.dto.common.RankDTO;
import com.ex.commercetestbackjpa.domain.dto.product.*;
import com.ex.commercetestbackjpa.domain.entity.product.Comment;
import com.ex.commercetestbackjpa.domain.entity.product.CommentImage;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import com.ex.commercetestbackjpa.domain.entity.product.ProductDT;
import com.ex.commercetestbackjpa.domain.entity.product.ProductImage;
import com.ex.commercetestbackjpa.domain.entity.product.ProductPrice;
import com.ex.commercetestbackjpa.repository.cache.CacheRepository;
import com.ex.commercetestbackjpa.repository.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtRepository productDtRepository;

    private final ProductPriceRepository productPriceRepository;

    private final ProductImageRepository productImageRepository;

    private final CommentRepository commentRepository;

    private final CommentImageRepository commentImageRepository;

    private final CacheRepository redisRepository;

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
        Optional<List<ProductImageDTO.Request>> productImageRequestDtoList = Optional.ofNullable(productRequestDto.getProductImageRequestDtoList());

        Long productNo = productRepository.save(product).getProductNo();

        // 단품 저장
        this.saveProductDt(productDTRequestDtoList, productNo);

        // 가격 저장
        this.saveProductPrice(productPriceRequestDtoList, productNo);

        // 이미지는 필수가 아니기 때문에 데이터 있을 때 저장
        productImageRequestDtoList.ifPresent(n -> this.saveProductImage(n, productNo));

        return productNo;
    }

    /**
     * ADMIN 상품 List 조회
     * @param filterMap
     * @param pageable
     * @return List<ProductDTO.Response>
     */
    public List<ProductDTO.Response> findProductForManage(Map<String, String> filterMap, Pageable pageable) {
        List<ProductDTO.Response> result = new ArrayList<>();
        Page<Product> productList = productRepository.findByFilters(filterMap, pageable);

        for(Product product : productList.getContent()) {
            ProductDTO.Response productResponseDto = new ProductDTO.Response(product);

            productResponseDto.addProductDtDtos(product.getProductDtList());
            productResponseDto.addCurrentProductPrice(product.getProductPriceList());
            productResponseDto.addProductImageDtos(product.getProductImageList());

            result.add(productResponseDto);
        }

        return result;
    }

    /**
     * 상품 List 조회
     * @param filterMap
     * @param pageable
     * @return List<ProductDTO.Response>
     */
    public List<ProductDTO.Response> findProductByFilters(Map<String, String> filterMap, Pageable pageable) {
        List<ProductDTO.Response> result = new ArrayList<>();
        Page<Product> productList = productRepository.findByFilters(filterMap, pageable);

        // Redis를 통하여 상품평 Rank 관리
        if (filterMap.get("keyword") != null) {
            redisRepository.sortSetAdd("searchRank", filterMap.get("keyword"), 1L);
        }

        for(Product product : productList.getContent()) {
            ProductDTO.Response productResponseDto = new ProductDTO.Response(product);

            productResponseDto.addCurrentProductPrice(product.getProductPriceList());
            productResponseDto.addProductImageDtos(product.getProductImageList());

            result.add(productResponseDto);
        }

        return result;
    }

    /**
     * 상품 List 조회
     * @param productNo
     * @return ProductDTO.Response
     */
    public ProductDTO.Response findProductByProductNo(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
        ProductDTO.Response productResponseDto = new ProductDTO.Response(product);

        productResponseDto.addProductDtDtos(product.getProductDtList());
        productResponseDto.addCurrentProductPrice(product.getProductPriceList());
        productResponseDto.addProductImageDtos(product.getProductImageList());
        productResponseDto.addProductCommentDtos(product.getProductCommentList());

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

        product.updateProductOptions(productRequestDto);

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

            productDt.updateProductDtOptions(productDTRequestDto);
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

    /**
     * 상품평 등록
     * @param commentRequestDto
     * @return Long
     */
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

        for(CommentImageDTO.Request commentImageDTO : commentRequestDto.getCommentImageRequestDtoList()) {
            CommentImage commentImage = commentImageDTO.toEntity();
            commentImage.settingImageName(FileUtil.uploadFile(commentImageDTO.getImgFile()));
            comment.addCommentImage(commentImage);
        }

        commentRepository.save(comment);
        product.updateCommentCount(product.getCommentCount()+1);

        return product.getProductNo();
    }

    /**
     * 인기검색어 조회
     * @return List<RankDTO.Response>
     */
    public List<RankDTO.Response> searchRankList(LocalDate date) {

        return redisRepository.sortSetFind("searchRank"+ Optional.ofNullable(date).orElse(LocalDate.now()));
    }

    /**
     * 상품평 변경
     * @param commentRequestDto
     * @return Long
     */
    @Transactional
    public Long updateComment(CommentDTO.Request commentRequestDto) {
        List<CommentImageDTO.Request> commentImageList = commentRequestDto.getCommentImageRequestDtoList();
        Comment comment = commentRepository.findById(commentRequestDto.getCommnetNo()).orElseThrow(() -> new NoSuchElementException("상품평 정보를 찾을 수 없습니다."));

        comment.updateCommentOptions(commentRequestDto);

        if(!commentImageList.isEmpty()) {
            for(CommentImageDTO.Request commentImage : commentImageList) {
                if(!commentImage.getRemove()) {
                    CommentImage commentImageEntity = commentImage.toEntity();
                    commentImageEntity.settingImageName(FileUtil.uploadFile(commentImage.getImgFile()));
                    comment.addCommentImage(commentImageEntity);
                } else {
                    FileUtil.deleteFile(commentImage.getImageName());
                    commentImageRepository.deleteById(commentImage.getCommentImageNo());
                }
            }
        }

        return commentRequestDto.getProductNo();
    }

    /**
     * 상품평 삭제
     * @param commentNo
     * @return Long
     */
    @Transactional
    public Long deleteComment(Long commentNo) {
        Comment comment = commentRepository.findById(commentNo).orElseThrow(() -> new NoSuchElementException("상품평 정보를 찾을 수 없습니다."));
        Product product = comment.getProduct();

        commentRepository.deleteById(commentNo);
        product.updateCommentCount(product.getCommentCount() - 1);

        return product.getProductNo();
    }
}
