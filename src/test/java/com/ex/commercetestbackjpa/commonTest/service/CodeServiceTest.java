package com.ex.commercetestbackjpa.commonTest.service;

import com.ex.commercetestbackjpa.domain.dto.common.CodeDTO;
import com.ex.commercetestbackjpa.repository.common.CodeRepository;
import com.ex.commercetestbackjpa.service.common.CommonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CodeServiceTest {

    @Autowired
    private CommonService commonService;

    @Test
    void 기초코드등록테스트() {
        List<CodeDTO.Request> codeRequestDtoList = new ArrayList<>();
        CodeDTO.Request codeRequestDto = new CodeDTO.Request();

        codeRequestDto.setCodeLgroup("0000");
        codeRequestDto.setCodeMgroup("A000");
        codeRequestDto.setCodeSgroup("상품판매구분");
        codeRequestDto.setCodeName("saleFlag");

        codeRequestDtoList.add(codeRequestDto);

        CodeDTO.Request codeRequestDto2 = new CodeDTO.Request();

        codeRequestDto2.setCodeLgroup("A000");
        codeRequestDto2.setCodeMgroup("00");
        codeRequestDto2.setCodeSgroup("판매진행");

        codeRequestDtoList.add(codeRequestDto2);

        CodeDTO.Request codeRequestDto3 = new CodeDTO.Request();

        codeRequestDto3.setCodeLgroup("A000");
        codeRequestDto3.setCodeMgroup("10");
        codeRequestDto3.setCodeSgroup("일시중단");

        codeRequestDtoList.add(codeRequestDto3);

        CodeDTO.Request codeRequestDto4 = new CodeDTO.Request();

        codeRequestDto4.setCodeLgroup("A000");
        codeRequestDto4.setCodeMgroup("20");
        codeRequestDto4.setCodeSgroup("판매중단");

        codeRequestDtoList.add(codeRequestDto4);

        commonService.saveCodeList(codeRequestDtoList);
    }
}
