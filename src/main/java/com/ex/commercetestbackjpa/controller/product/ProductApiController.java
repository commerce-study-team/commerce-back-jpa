package com.ex.commercetestbackjpa.controller.product;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTRequestDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductRequestDto;
import com.ex.commercetestbackjpa.domain.dto.product.ProductResponseDto;
import com.ex.commercetestbackjpa.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/save")
    public Long saveProduct (@RequestBody @Valid ProductRequestDto productRequestDto) {
        return productService.saveProduct(productRequestDto);
    }

    @GetMapping("/findProductNo/{productNo}")
    public ProductResponseDto findProductByProductNo (@PathVariable Long productNo) {
        return productService.findProductByProductNo(productNo);
    }

    @GetMapping("/findProductName/{productName}")
    public Map<String, Object> findProductByProductName (@PathVariable String ProductName) {
        return productService.findProductByProductName(ProductName);
    }

    @PutMapping("/updateColor")
    public Long updateProductDtColor(@RequestBody @Valid ProductDTRequestDto productDTRequestDto) {
        return productService.updateProductDtColor(productDTRequestDto);
    }

    @PutMapping("/updateSize")
    public Long updateProductDtSize(@RequestBody @Valid ProductDTRequestDto productDTRequestDto) {
        return productService.updateProductDtSize(productDTRequestDto);
    }

    @GetMapping("")
    public Map<String, Object> findProductAll () {
        return productService.findProductAll();
    }
}
