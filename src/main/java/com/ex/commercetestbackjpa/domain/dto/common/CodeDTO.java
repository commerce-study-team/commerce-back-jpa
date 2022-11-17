package com.ex.commercetestbackjpa.domain.dto.common;

import com.ex.commercetestbackjpa.domain.entity.common.Code;
import lombok.*;

import javax.persistence.Column;

public class CodeDTO {

    @Getter
    @Setter
    public static class Request {

        private String codeLgroup;

        private String codeMgroup;

        private String codeSgroup;

        private String codeName;

        private String remark;

        public Code toEntity() {
            return Code.builder()
                    .codeLgroup(this.codeLgroup)
                    .codeMgroup(this.codeMgroup)
                    .codeSgroup(this.codeSgroup)
                    .codeName(this.codeName)
                    .remark(this.remark)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Response {

        private String codeLgroup;

        private String codeMgroup;

        private String codeSgroup;

        private String codeName;

        private String remark;

        public Response(Code code) {
            this.codeLgroup = code.getCodeLgroup();
            this.codeMgroup = code.getCodeMgroup();
            this.codeSgroup = code.getCodeSgroup();
            this.codeName = code.getCodeName();
            this.remark = code.getRemark();
        }

    }
}
