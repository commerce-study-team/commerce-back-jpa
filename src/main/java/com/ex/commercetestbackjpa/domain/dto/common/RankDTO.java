package com.ex.commercetestbackjpa.domain.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.ZSetOperations;

public class RankDTO {

    @Getter
    @Setter
    public static class Response {

        private String itemId;

        private Double score;

        public Response(ZSetOperations.TypedTuple<String> typedTuples) {
            this.setItemId(typedTuples.getValue());
            this.setScore(typedTuples.getScore());
        }

    }
}
