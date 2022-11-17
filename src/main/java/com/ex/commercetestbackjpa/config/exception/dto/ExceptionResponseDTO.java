package com.ex.commercetestbackjpa.config.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseDTO {
    String code;
    String massage;

    @Builder
    public ExceptionResponseDTO(String code, String massage) {
        this.code = code;
        this.massage= massage;
    }
}
