package com.ex.commercetestbackjpa.repository.cache;

import com.ex.commercetestbackjpa.domain.dto.common.RankDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisRepository implements CacheRepository {

    private final RedisTemplate redisTemplate;

    @Override
    public void sortSetAdd(String key, String item, Long score) {
        if(this.hasKey(key, item)) {
            redisTemplate.opsForZSet().incrementScore(getKey(key), item,score);
        } else {
            redisTemplate.opsForZSet().add(getKey(key), item, score);
            redisTemplate.expire(getKey(key), 2, TimeUnit.DAYS);
        }
    }

    @Override
    public List<RankDTO.Response> sortSetFind(String key) {

        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 9);
        return typedTuples.stream().map(m -> new RankDTO.Response(m)).collect(Collectors.toList());
    }

    private Boolean hasKey(String key, String item) {

        Double exist = redisTemplate.opsForZSet().score(getKey(key), item);

        return exist != null;
    }

    private String getKey(String key) {
        return key + LocalDate.now();
    }
}
