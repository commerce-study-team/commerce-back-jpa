package com.ex.commercetestbackjpa.controller.product;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDtDTO;
import com.ex.commercetestbackjpa.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(tags = {"상품조회 및 저장 Controller"})
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @ApiOperation(value = "상품 저장 메소드")
    @PostMapping("/save")
    public Long saveProduct (@RequestBody @Valid ProductDTO.Request productRequestDto) {
        return productService.saveProduct(productRequestDto);
    }

    @ApiOperation(value = "상품코드 조회 메소드")
    @GetMapping("/findProductNo/{productNo}")
    public ProductDTO.Response findProductByProductNo (@PathVariable Long productNo) {
        return productService.findProductByProductNo(productNo);
    }

    @ApiOperation(value = "상품명 조회 메소드")
    @GetMapping("/findProductName/{productName}")
    public Map<String, Object> findProductByProductName (@PathVariable String ProductName) {
        return productService.findProductByProductName(ProductName);
    }

    @ApiOperation(value = "단품 저장 메소드")
    @PutMapping("/save/productDt")
    public Long saveProductDt(@RequestBody @Valid ProductDtDTO.Request productDTRequestDto) {
        return productService.saveProductDt(productDTRequestDto);
    }

    @ApiOperation(value = "단품색상 변경 메소드")
    @PutMapping("/updateColor")
    public Long updateProductDtColor(@RequestBody @Valid ProductDtDTO.Request productDTRequestDto) {
        return productService.updateProductDtColor(productDTRequestDto);
    }

    @ApiOperation(value = "단품사이즈 변경 메소드")
    @PutMapping("/updateSize")
    public Long updateProductDtSize(@RequestBody @Valid ProductDtDTO.Request productDTRequestDto) {
        return productService.updateProductDtSize(productDTRequestDto);
    }

    @ApiOperation(value = "전체상품 조회 메소드")
    @GetMapping("")
    public Map<String, Object> findProductAll () {
        return productService.findProductAll();
    }
}
