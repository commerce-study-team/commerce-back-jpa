package com.ex.commercetestbackjpa.controller.common;

import com.ex.commercetestbackjpa.domain.dto.common.CodeDTO;
import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.service.common.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"기초코드 조회 및 저장 Controller"})
@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonApiController {

    private final CommonService commonService;

    @ApiOperation(value = "기초 코드 저장")
    @PostMapping("/")
    public String saveCode(@RequestBody @Valid List<CodeDTO.Request> codeRequestDtoList) {
        return commonService.saveCodeList(codeRequestDtoList);
    }

}
