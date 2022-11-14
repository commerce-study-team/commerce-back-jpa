package com.ex.commercetestbackjpa.controller.product;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDtDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductImageDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductPriceDTO;
import com.ex.commercetestbackjpa.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = {"상품조회 및 저장 Controller"})
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @ApiOperation(value = "상품 저장")
    @PostMapping("")
    public Long saveProduct (@RequestBody @Valid ProductDTO.Request productRequestDto) {
        return productService.saveProduct(productRequestDto);
    }

    @ApiOperation(value = "상품 정보 변경")
    @PatchMapping("")
    public Long updateProduct (@RequestBody @Valid ProductDTO.Request productRequestDto) {
        return productService.updateProduct(productRequestDto);
    }

    @ApiOperation(value = "상품 단일 조회")
    @GetMapping("/{productNo}")
    public ProductDTO.Response findProductByProductNo (@PathVariable Long productNo) {
        return productService.findProductByProductNo(productNo);
    }

    @ApiOperation(value = "상품 List 조회")
    @GetMapping("")
    public Map<String, Object> findProductByFilters (@RequestParam Map<String, String> filterMap,
                                                     @PageableDefault(size=10, sort="productNo", direction = Sort.Direction.DESC) Pageable pageable) {

        return productService.findProductByFilters(filterMap, pageable);
    }

    @ApiOperation(value = "단품 저장")
    @PostMapping("/{productNo}/dt")
    public Long saveProductDt(@RequestBody @Valid List<ProductDtDTO.Request> productDTRequestDtoList, @PathVariable Long productNo) {
        return productService.saveProductDt(productDTRequestDtoList, productNo);
    }

    @ApiOperation(value = "단품 변경")
    @PatchMapping("/{productNo}/dt")
    public Long updateProductDtColor(@RequestBody @Valid List<ProductDtDTO.Request> productDTRequestDtoList, @PathVariable Long productNo) {
        return productService.updateProductDt(productDTRequestDtoList, productNo);
    }

    @ApiOperation(value = "가격 저장")
    @PostMapping("/{productNo}/price")
    public Long saveProductPrice(@RequestBody @Valid List<ProductPriceDTO.Request> productPriceRequestDtoList, @PathVariable Long productNo) {
        return productService.saveProductPrice(productPriceRequestDtoList, productNo);
    }

    @ApiOperation(value = "가격 변경")
    @PatchMapping("/{productNo}/price")
    public Long updateProductPrice(@RequestBody @Valid List<ProductPriceDTO.Request> productPriceRequestDtoList, @PathVariable Long productNo) {
        return productService.updateProductPrice(productPriceRequestDtoList, productNo);
    }

    @ApiOperation(value = "상품 이미지 추가")
    @PostMapping("/{productNo}/image")
    public Long uploadProductImage(@RequestBody @Valid List<ProductImageDTO.Request> productImageRequestDtoList, @PathVariable Long productNo) {

        return productService.saveProductImage(productImageRequestDtoList, productNo);
    }

    //상품 이미지 삭제 처리 추가 todo
}
