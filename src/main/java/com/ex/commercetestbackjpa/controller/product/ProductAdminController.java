package com.ex.commercetestbackjpa.controller.product;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDtDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductImageDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductPriceDTO;
import com.ex.commercetestbackjpa.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = {"상품조회 및 저장 Controller (admin)"})
@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;
    @ApiOperation(value = "상품 저장 (admin)")
    @PostMapping("")
    public ResponseEntity<Long> saveProduct (@RequestBody @Valid ProductDTO.Request productRequestDto) {
        long resProductNo = productService.saveProduct(productRequestDto);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "상품 정보 변경 (admin)")
    @PatchMapping("")
    public ResponseEntity<Long> updateProduct (@RequestBody @Valid ProductDTO.Request productRequestDto) {
        long resProductNo = productService.updateProduct(productRequestDto);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "상품 List 조회 (admin)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", dataType = "String", value = "상품명"),
            @ApiImplicitParam(name = "keyword", dataType = "String", value = "키워드"),
            @ApiImplicitParam(name = "lgroup", dataType = "String", value = "대분류"),
            @ApiImplicitParam(name = "mgroup", dataType = "String", value = "중분류"),
            @ApiImplicitParam(name = "sgroup", dataType = "String", value = "소분류"),
            @ApiImplicitParam(name = "saleFlag", dataType = "String", value = "판매구분"),
            @ApiImplicitParam(name = "signFlag", dataType = "String", value = "승인단계")
    })
    @GetMapping("")
    public ResponseEntity<List<ProductDTO.Response>> findProductByFilters (@RequestParam(required = false) Map<String, String> filterMap,
                                                     @PageableDefault(size=10, sort="productNo", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ProductDTO.Response> list = productService.findProductForManage(filterMap, pageable);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "단품 저장 (admin)")
    @PostMapping("/{productNo}/dt")
    public ResponseEntity<Long> saveProductDt(@RequestBody @Valid List<ProductDtDTO.Request> productDTRequestDtoList, @PathVariable Long productNo) {
        long resProductNo = productService.saveProductDt(productDTRequestDtoList, productNo);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "단품 변경 (admin)")
    @PatchMapping("/{productNo}/dt")
    public ResponseEntity<Long> updateProductDtColor(@RequestBody @Valid List<ProductDtDTO.Request> productDTRequestDtoList, @PathVariable Long productNo) {
        long resProductNo = productService.updateProductDt(productDTRequestDtoList, productNo);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "가격 저장 (admin)")
    @PostMapping("/{productNo}/price")
    public ResponseEntity<Long> saveProductPrice(@RequestBody @Valid List<ProductPriceDTO.Request> productPriceRequestDtoList, @PathVariable Long productNo) {
        long resProductNo = productService.saveProductPrice(productPriceRequestDtoList, productNo);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "가격 변경 (admin)")
    @PatchMapping("/{productNo}/price")
    public ResponseEntity<Long> updateProductPrice(@RequestBody @Valid List<ProductPriceDTO.Request> productPriceRequestDtoList, @PathVariable Long productNo) {
        long resProductNo = productService.updateProductPrice(productPriceRequestDtoList, productNo);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "상품 이미지 추가 (admin)")
    @PostMapping("/{productNo}/image")
    public ResponseEntity<Long> uploadProductImage(@RequestBody @Valid List<ProductImageDTO.Request> productImageRequestDtoList, @PathVariable Long productNo) {
        long resProductNo = productService.saveProductImage(productImageRequestDtoList, productNo);
        return ResponseEntity.ok().body(resProductNo);
    }

    @ApiOperation(value = "상품 이미지 삭제 (admin)")
    @DeleteMapping("/{productImageNo}/image")
    public ResponseEntity<Long> deleteProductImage(@PathVariable Long productImageNo) {
        long resProductNo = productService.deleteProductImage(productImageNo);
        return ResponseEntity.ok().body(resProductNo);
    }
}