package com.ex.commercetestbackjpa.service.common;

import com.ex.commercetestbackjpa.domain.dto.common.CodeDTO;
import com.ex.commercetestbackjpa.repository.common.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final CodeRepository codeRepository;

    @Transactional
    public String saveCodeList(List<CodeDTO.Request> codeRequestDtoList) {
        for(CodeDTO.Request codeRequestDto : codeRequestDtoList) {
            codeRepository.save(codeRequestDto.toEntity());
        }

        return codeRequestDtoList.get(0).getCodeLgroup();
    }


}
