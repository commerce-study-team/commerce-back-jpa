package com.ex.commercetestbackjpa.controller.product;

import com.ex.commercetestbackjpa.domain.dto.comment.CommentDTO;
import com.ex.commercetestbackjpa.domain.dto.common.RankDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Api(tags = {"Front 상품 Controller"})
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @ApiOperation(value = "메인페이지 상품 List 조회 (front)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", dataType = "String", value = "상품명"),
            @ApiImplicitParam(name = "keyword", dataType = "String", value = "키워드"),
            @ApiImplicitParam(name = "lgroup", dataType = "String", value = "대분류"),
            @ApiImplicitParam(name = "mgroup", dataType = "String", value = "중분류"),
            @ApiImplicitParam(name = "sgroup", dataType = "String", value = "소분류")
    })
    @GetMapping("")
    public ResponseEntity<List<ProductDTO.Response>> findProductByFilters (@RequestParam(required = false) Map<String, String> filterMap,
                                                     @PageableDefault(size=10, sort="productNo", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ProductDTO.Response> list = productService.findProductByFilters(filterMap, pageable);

        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "상세 상품 조회 (front)")
    @GetMapping("/{productNo}")
    public ResponseEntity<ProductDTO.Response> findProductByProductNo (@PathVariable Long productNo) {
        ProductDTO.Response response = productService.findProductByProductNo(productNo);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "상품 리뷰 저장")
    @PostMapping("/review")
    public ResponseEntity<Long> saveProductComment(@RequestBody @Valid CommentDTO.Request commentRequestDTO) {
        long resProductNo = productService.saveComment(commentRequestDTO);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "상품 리뷰 변경")
    @PatchMapping("/review")
    public ResponseEntity<Long> updateProductComment(@RequestBody @Valid CommentDTO.Request commentRequestDTO) {
        long resProductNo = productService.updateComment(commentRequestDTO);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "상품 리뷰 삭제")
    @DeleteMapping("/review/{commentNo}")
    public ResponseEntity<Long> deleteProductComment(@PathVariable Long commentNo) {
        long resProductNo = productService.deleteComment(commentNo);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "인기검색어 조회")
    @GetMapping("/searchRank")
    public ResponseEntity<List<RankDTO.Response>> SearchRankList(@RequestParam(value = "date", required = false)LocalDate date) {
        List<RankDTO.Response> list = productService.searchRankList(date);
        return ResponseEntity.ok().body(list);
    }

/*    @ApiOperation(value = "리뷰 Like")
    @PostMapping("/review/like")
    public Long updateReviewLike(@RequestParam(value = "commentNo") Long commentNo) {

        productService.updateReviewLike(commentNo);
    }*/

}
