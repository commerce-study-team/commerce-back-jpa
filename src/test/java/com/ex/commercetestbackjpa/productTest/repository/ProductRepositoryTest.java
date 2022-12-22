package com.ex.commercetestbackjpa.productTest.repository;

import com.ex.commercetestbackjpa.domain.entity.product.*;
import com.ex.commercetestbackjpa.repository.product.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDtRepository productDtRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentImageRepository commentImageRepository;

    @BeforeEach
    void saveProduct() {
        Product product = Product.builder()
                .productName("테스트상품등록")
                .lgroup("10")
                .mgroup("10")
                .sgroup("10")
                .keyword("테스트키워드")
                .build();

        productRepository.save(product);

        ProductDT productDT = ProductDT.builder()
                .productDtName("테스트상품단품등록")
                .colorCode("10")
                .colorName("빨강")
                .sizeCode("10")
                .sizeName("대")
                .saleFlag("00")
                .product(product)
                .build();

        productDtRepository.save(productDT);

        ProductPrice productPrice = ProductPrice.builder()
                .applyDate(LocalDateTime.now())
                .salePrice(1000L)
                .costPrice(500L)
                .product(product)
                .build();

        productPriceRepository.save(productPrice);

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "테스트이미지";

        ProductImage productImage = ProductImage.builder()
                .imageName("테스트이미지")
                .imageRealName(imageFileName)
                .build();
        productImage.settingProduct(product);

        productImageRepository.save(productImage);

        Comment comment = Comment.builder()
                .title("상품평 남깁니다!")
                .content("테스트리뷰입니다.")
                .grade(5)
                .deleteYn(false)
                .likes(0L)
                .build();

        comment.settingProduct(product);

        Comment commentCT = commentRepository.save(comment);
    }

    @Test
    void saveProductTest() {
        // given
        Product product = Product.builder()
                .productName("테스트상품등록")
                .lgroup("10")
                .mgroup("10")
                .sgroup("10")
                .keyword("테스트키워드")
                .build();

        // when
        Product productCT = productRepository.save(product);

        // then
        assertThat(product.getProductNo()).isEqualTo(productCT.getProductNo());
    }

    @Test
    void findByFilterTest() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "productNo");
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("keyword", "테스트키워드");

        // when
        Page<Product> productList = productRepository.findByFilters(filterMap, pageable);

        // then
        assertThat("테스트키워드").isEqualTo(productList.getContent().get(0).getKeyword());
    }

    @Test
    void saveProductDtTest() {
        // given
        Product product = Product.builder()
                .productName("테스트상품등록")
                .lgroup("10")
                .mgroup("10")
                .sgroup("10")
                .keyword("테스트키워드")
                .build();

        ProductDT productDT = ProductDT.builder()
                .productDtName("테스트상품단품등록")
                .colorCode("10")
                .colorName("빨강")
                .sizeCode("10")
                .sizeName("대")
                .saleFlag("00")
                .product(product)
                .build();

        // when
        ProductDT productDTCT = productDtRepository.save(productDT);

        // then
        assertThat(productDT.getProductDtNo()).isEqualTo(productDTCT.getProductDtNo());
    }

    @Test
    void findProductDtListTest() {
        // given

        // when
        List<ProductDT> productDTList = productDtRepository.findAll();

        // then
        assertThat("0").isEqualTo(productDTList.get(0).getSaleFlag());
    }

    @Test
    void saveProductPriceTest() {
        // given
        Product product = Product.builder()
                .productName("테스트상품등록")
                .lgroup("10")
                .mgroup("10")
                .sgroup("10")
                .keyword("테스트키워드")
                .build();

        ProductPrice productPrice = ProductPrice.builder()
                .applyDate(LocalDateTime.now())
                .salePrice(1000L)
                .costPrice(500L)
                .product(product)
                .build();

        // when
        ProductPrice productPriceCT = productPriceRepository.save(productPrice);

        // then
        assertThat(productPrice.getProductPriceNo()).isEqualTo(productPriceCT.getProductPriceNo());
    }

    @Test
    void saveProductImageTest() {
        // given
        Product product = Product.builder()
                .productName("테스트상품등록")
                .lgroup("10")
                .mgroup("10")
                .sgroup("10")
                .keyword("테스트키워드")
                .build();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "테스트.png";

        ProductImage productImage = ProductImage.builder()
                .imageName("테스트.png")
                .imageRealName(imageFileName)
                .build();
        productImage.settingProduct(product);

        // when
        ProductImage productImageCT = productImageRepository.save(productImage);

        // then
        assertThat(productImage.getImageRealName()).isEqualTo(productImageCT.getImageRealName());
    }

    @Test
    void findProductImageListTest() {
        // given

        // when
        List<ProductImage> productImageList = productImageRepository.findAll();

        // then
        assertThat("테스트.png").isEqualTo(productImageList.get(0).getImageRealName());
    }

    @Test
    void saveCommentTest() {
        // given
        Product product = Product.builder()
                .productName("테스트상품등록")
                .lgroup("10")
                .mgroup("10")
                .sgroup("10")
                .keyword("테스트키워드")
                .build();

        Comment comment = Comment.builder()
                .title("상품평 남깁니다!")
                .content("테스트리뷰입니다.")
                .grade(5)
                .deleteYn(false)
                .likes(0L)
                .build();

        comment.settingProduct(product);

        // when
        Comment commentCT = commentRepository.save(comment);

        // then
        assertThat(comment.getTitle()).isEqualTo(commentCT.getTitle());
    }

    @Test
    void findCommentList() {
        // given

        // when
        List<Comment> commentList = commentRepository.findAll();

        // then
        assertThat("상품평 남깁니다!").isEqualTo(commentList.get(0).getTitle());
    }

    @Test
    void saveCommentImageTest() {
        // given
        Product product = Product.builder()
                .productName("테스트상품등록")
                .lgroup("10")
                .mgroup("10")
                .sgroup("10")
                .keyword("테스트키워드")
                .build();

        Comment comment = Comment.builder()
                .title("상품평 남깁니다!")
                .content("테스트리뷰입니다.")
                .grade(5)
                .deleteYn(false)
                .likes(0L)
                .build();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "테스트.png";

        CommentImage commentImage = CommentImage.builder()
                .imageName(imageFileName)
                .imageRealName("테스트.png")
                .build();

        commentImage.addComment(comment);

        // when
        CommentImage commentImageCT = commentImageRepository.save(commentImage);

        // then
        assertThat(commentImage.getImageRealName()).isEqualTo(commentImageCT.getImageRealName());
    }

    @Test
    void findCommentImage() {
        // given

        // when
        List<CommentImage> commentImageList = commentImageRepository.findAll();

        // then
        assertThat("테스트.png").isEqualTo(commentImageList.get(0).getImageRealName());
    }
}
