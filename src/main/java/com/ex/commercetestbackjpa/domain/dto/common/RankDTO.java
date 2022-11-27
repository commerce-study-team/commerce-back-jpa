package com.ex.commercetestbackjpa.domain.dto.common;

import com.ex.commercetestbackjpa.domain.entity.common.Code;
import lombok.Getter;
import lombok.Setter;

public class RankDTO {

    @Getter
    @Setter
    public static class Response {

        private Long itemId;

        private Long score;

    }
}
