package com.ex.commercetestbackjpa.controller.product;

import com.ex.commercetestbackjpa.domain.dto.comment.CommentDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

import javax.validation.Valid;
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
    public List<ProductDTO.Response> findProductByFilters (@RequestParam(required = false) Map<String, String> filterMap,
                                                     @PageableDefault(size=10, sort="productNo", direction = Sort.Direction.DESC) Pageable pageable) {

        return productService.findProductByFilters(filterMap, pageable);
    }

    @ApiOperation(value = "상세 상품 조회 (front)")
    @GetMapping("/{productNo}")
    public ProductDTO.Response findProductByProductNo (@PathVariable Long productNo) {

        return productService.findProductByProductNo(productNo);
    }

    @ApiOperation(value = "상품 리뷰 저장")
    @PostMapping("/review")
    public Long saveProductComment(@RequestBody @Valid CommentDTO.Request commentRequestDTO) {
        return productService.saveComment(commentRequestDTO);
    }

    @ApiOperation(value = "상품 리뷰 변경")
    @PatchMapping("/review")
    public Long updateProductComment(@RequestBody @Valid CommentDTO.Request commentRequestDTO) {
        return productService.updateComment(commentRequestDTO);
    }

    @ApiOperation(value = "상품 리뷰 삭제")
    @DeleteMapping("/review/{commentNo}")
    public Long deleteProductComment(@PathVariable Long commentNo) {
        return productService.deleteComment(commentNo);
    }
}
