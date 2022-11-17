package com.ex.commercetestbackjpa.commonTest.repository;

import com.ex.commercetestbackjpa.domain.dto.common.CodeDTO;
import com.ex.commercetestbackjpa.repository.common.CodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CodeRepositoryTest {

    @Autowired
    private CodeRepository codeRepository;

    @Test
    void 기초코드등록테스트() {
        CodeDTO.Request codeRequestDto = new CodeDTO.Request();

        codeRequestDto.setCodeLgroup("0000");
        codeRequestDto.setCodeMgroup("상품판매구분");
        codeRequestDto.setCodeSgroup("saleFlag");

        String lgroup = codeRepository.save(codeRequestDto.toEntity()).getCodeLgroup();

        assertThat(lgroup).isEqualTo(codeRequestDto.getCodeLgroup());
    }

    @Test
    void 기초코드실패테스트() {
        CodeDTO.Request codeRequestDto = new CodeDTO.Request();

        codeRequestDto.setCodeLgroup("0000");
        codeRequestDto.setCodeMgroup("상품판매구분");
        codeRequestDto.setCodeSgroup("saleFlag");

        Assertions.assertThrows(SQLException.class, () -> codeRepository.save(codeRequestDto.toEntity()));
    }
}
